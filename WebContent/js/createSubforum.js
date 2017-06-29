$(document).ready(function(){
	
	
	$("#cancel").click(function(){
		window.location.href = "home.html";
	});
	
	$("#logout").click(function() {

		$.ajax({
			method : 'GET',
			url : "../ms.forum/rest/cookie/delete",
			contentType : 'application/json',
			success : function(data) {
				console.log("success");
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				console.log(textStatus);
				console.log(errorThrown);
			}
		});
		$.cookie("stevan", "");
		window.location.href = "home.html";
	});
	
});

$(document).on('submit', '#create-subforum-form', function(e) {
	
	e.preventDefault();
	console.log("creating subforum");
	var description = $("#subforumDescription").val();
	var name = $("#subforumName").val();
	var rules = $("#subforumRules").val();
	$.ajax({
		method : 'POST',
		url : "../ms.forum/rest/subforum/add",
		contentType : 'application/json',
		dataType : "text",
		data : registerformToJSON(name,rules,description),
		success : function(data) {
			window.location.href = "home.html";
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("AJAX ERROR: " + errorThrown + XMLHttpRequest);
		}
	});
	
});

function registerformToJSON(name,rules,description) {
	return JSON.stringify({
		"name" : name,
		"description" : description,
		"rules" : rules,
		"moderatorId" : null
		
	});
}