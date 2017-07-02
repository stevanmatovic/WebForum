$(document).ready(function() {

	$("#postSearch").show();
	$("#userSearch").hide();
	$("#subforumSearch").hide();
	$("#liPost").addClass("active");
	
	$(".nav").click(function(){
		if($(this).text() == "Search subforum"){
			$( "li" ).removeClass( "active" );
			$("#postSearch").hide();
			$("#userSearch").hide();
			$("#subforumSearch").show();
			$("#liSubforum").addClass("active");
		}
		else if($(this).text() == "Search post"){
			$( "li" ).removeClass( "active" );
			$("#postSearch").show();
			$("#userSearch").hide();
			$("#subforumSearch").hide();
			$("#liPost").addClass("active");
		}
		else if($(this).text() == "Search user"){
			$( "li" ).removeClass( "active" );
			$("#postSearch").hide();
			$("#userSearch").show();
			$("#subforumSearch").hide();
			$("#liUser").addClass("active");
		}
	});
	
	var modalReciever="";
	
	$(document).on('click','.userItem',function(e){
		
		if ($.cookie("web-forum") == null || $.cookie("web-forum") == undefined
				|| $.cookie("web-forum") == ""){
			return;
		}
		
		primalac = $(this).text();
		$("#sendMessageTo").text('Send message to '+primalac);
		$("#exampleModal").modal();
		modalReciever =  $(this).attr('id');
	})
	
	$(document).on('click','#send',function(e){
		id = modalReciever;
		text = $("#message-text").val();
		subject = $("#subject").val();
		
		$.ajax({
			type : 'POST',
			url : "../rs.ftn.mr.webforum/rest/message/add",
			contentType : 'application/json',
			data : formToJSON(id, subject,text),
			success : function() {
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				console.log(XMLHttpRequest)
			    console.log(errorThrown)
			    console.log(textStatus)
			}
		});
		
	})
	
	
	$("#searchUser").click(function(e){
    	e.preventDefault();
    	$.ajax({
    		method : 'POST',
    		url : "../ms.forum/rest/users/search",
    		contentType : 'application/json',
    		data: JSON.stringify({
    			"korisnik" : $("#korisnik").val(),
    		}),
    		success : function(data) {
    			$("#users").empty();
    			data.forEach(function(element) {
    				console.log(element);
    				
    				$( "#users" ).append( '<li id="'+element.id+'" class="list-group-item list-group-item-action userItem" style="cursor:pointer">'+element.username+'</li>');
    			});
    			
    		},
    		error : function(XMLHttpRequest, textStatus, errorThrown) {
    			console.log(textStatus);
    			console.log(errorThrown);
    		}
    	});
    });
	
	$("#searchSubforum").click(function(e){
    	e.preventDefault();
    	$.ajax({
    		method : 'POST',
    		url : "../ms.forum/rest/subforum/search",
    		contentType : 'application/json',
    		data: JSON.stringify({
    			"naslov" : $("#naslov").val(),
    			"opis" : $("#opis").val(),
    			"moderator" : $("#moderator").val(),
    		}),
    		success : function(data) {
    			$("#subforums").empty();
    			data.forEach(function(element) {
    				$( "#subforums" ).append( '<a href="home.html?name=' + element.id + '" class="list-group-item list-group-item-action">'+element.name+'</a>' );
    			});
    			
    		},
    		error : function(XMLHttpRequest, textStatus, errorThrown) {
    			console.log(textStatus);
    			console.log(errorThrown);
    		}
    	});
    });
	
	$("#searchPost").click(function(e){
    	e.preventDefault();
    	$.ajax({
    		method : 'POST',
    		url : "../ms.forum/rest/post/search",
    		contentType : 'application/json',
    		data: JSON.stringify({
    			"naslov" : $("#naslov").val(),
    			"sadrzaj" : $("#sadrzaj").val(),
    			"autor" : $("#autor").val(),
    			"podforum" : $("#podforum").val()
    		}),
    		success : function(data) {
    			$("#posts").empty();
    			data.forEach(function(element) {
    				$( "#posts" ).append( '<a href="tema.html?id=' + element.id + '" class="list-group-item list-group-item-action">'+element.naslov+'</a>' );
    			});
    			
    		},
    		error : function(XMLHttpRequest, textStatus, errorThrown) {
    			console.log(textStatus);
    			console.log(errorThrown);
    		}
    	});
    });
	
})

function formToJSON(prima, subject,tekst) {
	return JSON.stringify({
		"prima" : prima,
		"subject" : subject,
		"tekst" : tekst
	});
}
function getPodforum(){
	var id = getUrlVars()["locationId"];
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