$(document).ready(function() {
	
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
	
	$.ajax({
		method : 'POST',
		url : "../ms.forum/rest/post",
		contentType : 'text/plain',
		data: getUrlVars()["id"],
		success : function(data) {
			if(data.type=="picture"){
				$("#content").append('<img class="img-responsive" src="images/'+ data.picture+ '" class="img-responsive img-rounded">');
			}else if(data.type=="link"){
				$("#content").append('<p class = "text-center"><a href="'+data.link+'">'+data.link+'</a></p>');
			}else if(data.type=="text"){
				$("#content").append('<p class="lead text-justify">'+data.tekst+'</p>');
			}
			$("#name").text(data.title);
			$("#date").text('Created on: ' + data.date);
			$("#likeCount").text('Likes: ' + data.likes + ' Dislikes: '+data.dislikes );
			
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			console.log(textStatus);
			console.log(errorThrown);
		}
	});
	
	$.ajax({
		method : 'POST',
		url : "../ms.forum/rest/post/loadComments",
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
	
	
	
	$("#like").click(function(){
		$.ajax({
			method : 'POST',
			url : "/ms.forum/rest/post/like",
			contentType : 'application/json',
			data :  formLikeToJSON(true),
			success : function(data) {
				$("#likeCount").text('Likes: ' + data.likes + ' Dislikes: '+data.dislikes );
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				console.log(textStatus);
				console.log(errorThrown);
				window.location.href = "register.html"
			}
		});
	});
	$("#dislike").click(function(){
		$.ajax({
			method : 'POST',
			url : "/ms.forum/rest/post/like",
			contentType : 'application/json',
			data :  formLikeToJSON(false),
			success : function(data) {
				$("#likeCount").text('Likes: ' + data.likes + ' Dislikes: '+data.dislikes );
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				console.log(textStatus);
				console.log(errorThrown);
				window.location.href = "register.html"
			}
		});
	});
	
	$.ajax({
		method : 'POST',
		url : "../ms.forum/rest/post/getAuthor",
		contentType : 'text/plain',
		data: getUrlVars()["id"],
		success : function(data) {
			$("#authorName").text('Author: ' + data);
			
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
	
	
});

function commentToJSON() {
	var postId= getUrlVars()["id"];
	postId = postId.replace("#","");
	return JSON.stringify({
		"id":null,
		"postId":postId,
		"authorId": null,
		"date": null,
		"parentId": -1,
		"text": $("#comment").val(),
		"changed": "false",
		"deleted" : "false"
		
	});
}

function formLikeToJSON(like) {
	return JSON.stringify({
		"id" : null,
		"idTema" : getUrlVars()["id"],
		"idUser" : null,
		"like" : like
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
