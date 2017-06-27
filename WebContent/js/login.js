var rootURL = "../ms.forum/rest/users/login";
$(document).on('submit', '#register-form', function(e) {
	e.preventDefault();
	console.log("register");
//	var data = $('.productsform').serialize();
	var username = $(this).find("#username").val();
	var password = $(this).find("#password").val();
	$.ajax({
		method : 'POST',
		url : rootURL,
		contentType : 'application/json',
		dataType : "text",
		data : loginformToJSON(username,password),
		success : function(data) {
			window.location.replace("http://localhost:8080/ms.forum/home.html");
			var d = JSON.parse(data);
			$.cookie("stevan", d.id,{expires: new Date(2017,10,29,11,00,00)});
			console.log(getCookie("stevan"));
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("AJAX ERROR: " + errorThrown + XMLHttpRequest);
		}
	});
});

$(document).ready(function() {
	if($.cookie("stevan") != null && $.cookie("stevan") != undefined && $.cookie("stevan") != ""){
		window.location.replace("http://localhost:8080/ms.forum/home.html");		
	}
});

function loginformToJSON(username,password) {
	return JSON.stringify({
		"username" : username,
		"password" : password
	});
}