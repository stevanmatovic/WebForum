$(document).ready(function() {

	$("#create-text-post").show();
	$("#create-link-post").hide();
	$("#create-picture-post").hide();
	$("#liText").addClass("active");
	
	$("a").click(function(){
		if($(this).text() == "Picture"){
			$( "li" ).removeClass( "active" );
			$("#create-text-post").hide();
			$("#create-link-post").hide();
			$("#create-picture-post").show();
			$("#liPicture").addClass("active");
		}
		else if($(this).text() == "Text"){
			$( "li" ).removeClass( "active" );
			$("#create-text-post").show();
			$("#create-link-post").hide();
			$("#create-picture-post").hide();
			$("#liText").addClass("active");
		}
		else if($(this).text() == "Link"){
			$( "li" ).removeClass( "active" );
			$("#create-text-post").hide();
			$("#create-link-post").show();
			$("#create-picture-post").hide();
			$("#liLink").addClass("active");
		}
	});
	
	$(document).on('submit','#create-text-post',function(e){
		
		var title = $('#textPostName').val();
		var text = $('#postText').val();
		$.ajax({
    		
    		method : 'POST',
    		url : "../ms.forum/rest/post/add",
    		contentType : 'application/json',
    		dataType : "text",
    		data : textPostToJSON(title,text),
    		success : function(data) {
    			console.log(data);
    		},
    		error : function(XMLHttpRequest, textStatus, errorThrown) {
    			
    		}
    		
    	})
	});
	
	$(document).on('submit','#create-link-post',function(e){
		
		var title = $('#linkPostName').val();
		var link = $('#postLink').val();
		$.ajax({
    		
    		method : 'POST',
    		url : "../ms.forum/rest/post/add",
    		contentType : 'application/json',
    		dataType : "text",
    		data : linkPostToJSON(title,link),
    		success : function(data) {
    			console.log(data);
    		},
    		error : function(XMLHttpRequest, textStatus, errorThrown) {
    			
    		}
    		
    	})
	});
	
	$(document).on('submit','#create-picture-post',function(e){	
		e.preventDefault();
		var data = new FormData(this);
		
		var file = $('#pictureLink')[0].files[0];
		
		 $.ajax({
		        url : "../ms.forum/rest/post/upload",
		        type : "POST",
		        contentType : "multipart/form-data",
		        dataType: "JSON",
		        data: file,
		        processData: false,
		        async: false,
		        complete: function(data) {
		        	var id = data.responseText;
		        	
		        	console.log(imagePostToJSON(id));
		        	
		        	$.ajax({
		        		
		        		method : 'POST',
		        		url : "../ms.forum/rest/post/add",
		        		contentType : 'application/json',
		        		dataType : "text",
		        		data : imagePostToJSON(id),
		        		success : function(data) {
		        			console.log(data);
		        		},
		        		error : function(XMLHttpRequest, textStatus, errorThrown) {
		        			
		        		}
		        		
		        	})
		        }
		 });
		
	})
		
	
	$("#pictureLink").change(function(){
	    readURL(this);
	});
	
});

function readURL(input) {
	 
    if (input.files && input.files[0]) {
        var reader = new FileReader();
 
        reader.onload = function (e) {
            $('#picture').attr('src', e.target.result);
        }
 
        reader.readAsDataURL(input.files[0]);
    }
}

function imagePostToJSON(id) {
	var subforumId= getUrlVars()["name"];
	subforumId = subforumId.replace("#","");
	return JSON.stringify({
		"id" : null,
		"subforumId" : null,
		"title" : $("#picturePostName").val(),
		"type" : "picture",
		"autorId" : null,
		"picture" : id,
		"tekst" : null,
		"link" : null,
		"date" : null,
		"help" : subforumId
		
	});
}


function textPostToJSON(title,text) {
	var subforumId= getUrlVars()["name"];
	subforumId = subforumId.replace("#","");
	return JSON.stringify({
		"id" : null,
		"subforumId" : null,
		"title" : title,
		"type" : "text",
		"autorId" : null,
		"picture" : null,
		"tekst" : text,
		"link" : null,
		"date" : null,
		"help" : subforumId
		
	});
}

function linkPostToJSON(title,link) {
	var subforumId= getUrlVars()["name"];
	subforumId = subforumId.replace("#","");
	return JSON.stringify({
		"id" : null,
		"subforumId" : null,
		"title" : title,
		"type" : "link",
		"autorId" : null,
		"picture" : null,
		"tekst" : null,
		"link" : link,
		"date" : null,
		"help" : subforumId
		
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
