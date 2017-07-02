$(document).ready(function() {
	
	var commentId = getUrlVars()["id"];
	if(commentId == "" || commentId==null || commentId==undefined)
		window.location.href="home.html";
	
	
	$.ajax({
		method : 'POST',
		url : "../ms.forum/rest/comment/get",
		contentType : 'text/plain',
		data: commentId,
		success : function(data) {
			$("#heading").text(data.text);
			$("#date").text(data.date);
			$("#likeCount").text('Likes: ' + data.likes + ' Dislikes: '+data.dislikes );			
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			console.log(textStatus);
			console.log(errorThrown);
		}
	});
	
	$.ajax({
		method : 'POST',
		url : "../ms.forum/rest/comment/getAuthor",
		contentType : 'text/plain',
		data: commentId,
		success : function(data) {
			$("#authorName").text('Author: '+data.username);
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			console.log(textStatus);
			console.log(errorThrown);
		}
	});
	
	$("#like").click(function(){
		$.ajax({
			method : 'POST',
			url : "../ms.forum/rest/comment/like",
			contentType : 'application/json',
			data: JSON.stringify({
				"idKomentar" : commentId,
				"like" : true
			}),
			success : function(data) {
				$("#likeCount").text('Likes: ' + data.likes + ' Dislikes: '+data.dislikes );
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				console.log(textStatus);
				console.log(errorThrown);
			}
		});
	});
	$("#dislike").click(function(){
		$.ajax({
			method : 'POST',
			url : "../ms.forum/rest/comment/like",
			contentType : 'application/json',
			data: JSON.stringify({
				"idKomentar" : commentId,
				"like" : false
			}),
			success : function(data) {
				$("#likeCount").text('Likes: ' + data.likes + ' Dislikes: '+data.dislikes );
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				console.log(textStatus);
				console.log(errorThrown);
			}
		});
	});
	
	
	$.ajax({
		method : 'POST',
		url : "../ms.forum/rest/comment/subcomments",
		contentType : 'text/plain',
		data: getUrlVars()["id"],
		success : function(data) {
			data.forEach(function(element) {
				str='<div id="'+element.id +'" class="row panel panel-default "><p class="commentDiv" style="cursor:pointer">';
				str+=element.text + '</p><b>Likes: ' + element.likes;
				str+=' Dislikes: ' + element.dislikes + '</b>';
				str+='<button type="button" class="btn btn-basic pull-right dislikeComment">'
    			str+='<span class="glyphicon glyphicon-thumbs-down"></span>'
  				str+='</button><button type="button"  class="btn btn-basic pull-right likeComment">'
  				str+='<span class="glyphicon glyphicon-thumbs-up"></span></button></div>'	
					
				$("#comments").append(str);
				$("#comment").val("");
			});
			
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			console.log(textStatus);
			console.log(errorThrown);
		}
	});
	
	
	$(document).on('submit','#commentForm',function(e){
		e.preventDefault();

		$.ajax({
			method : 'POST',
			url : "../ms.forum/rest/comment/add",
			contentType : 'application/json',
			data: commentToJSON(),
			success : function(element) {
				str='<div id="'+element.id +'" class="row panel panel-default"><p class="commentDiv" style="cursor:pointer">';
				str+=element.text + '</p><b>Likes: ' + element.likes;
				str+=' Dislikes: ' + element.dislikes + '</b>';
				str+='<button type="button" class="btn btn-basic pull-right likeComment">'
    			str+='<span class="glyphicon glyphicon-thumbs-down"></span>'
  				str+='</button><button type="button"  class="btn btn-basic pull-right dislikeComment">'
  				str+='<span class="glyphicon glyphicon-thumbs-up"></span></button></div>'	
					
				$("#comments").append(str);
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				console.log(textStatus);
				console.log(errorThrown);
			}
		});
		
	})
	
	$(document).on("click",".likeComment",function() {
		par = $(this).parent();
		commentId=$(this).parent().attr("id");
		
		$.ajax({
			method : 'POST',
			url : "../ms.forum/rest/comment/like",
			contentType : 'application/json',
			data: JSON.stringify({
				"idKomentar" : commentId,
				"like" : true
			}),
			success : function(data) {
				par.find("b").text('Likes: '+data.likes + ' Dislikes: ' + data.dislikes);
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				console.log(textStatus);
				console.log(errorThrown);
			}
		});
		
		
	});
	
	$(document).on("click",".commentDiv",function() {
		
		id = $(this).parent().attr("id");
		window.location.href = "comment.html?id="+id;	
	})
	
	$(document).on("click",".dislikeComment",function() {
		par = $(this).parent();
		commentId=$(this).parent().attr("id");
		
		$.ajax({
			method : 'POST',
			url : "../ms.forum/rest/comment/like",
			contentType : 'application/json',
			data: JSON.stringify({
				"idKomentar" : commentId,
				"like" : false
			}),
			success : function(data) {
				par.find("b").text('Likes: '+data.likes + ' Dislikes: ' + data.dislikes);
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				console.log(textStatus);
				console.log(errorThrown);
			}
		});
	});
	
})

function commentToJSON() {
	var parent= getUrlVars()["id"];
	parent = parent.replace("#","");
	return JSON.stringify({
		"id":null,
		"postId": -1,
		"authorId": null,
		"date": null,
		"parentId": parent,
		"text": $("#comment").val(),
		"changed": "false",
		"deleted" : "false"
		
	});
}

function getUrlVars()
{
    var vars = [], hash;
    var hashes = window.location.href.slice(window.location.href.indexOf('?') + 1).split('&');
    for(var i = 0; i < hashes.length; i++)
    {
        hash = hashes[i].split('=');
        vars.push(hash[0]);
        vars[hash[0]] = hash[1];
    }
    return vars;
}