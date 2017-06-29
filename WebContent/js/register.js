var rootURL = "../ms.forum/rest/users/add";
$(document).on('submit', '#register-form', function(e) {
	e.preventDefault();
	console.log("register");
//	var data = $('.productsform').serialize();
	var username = $(this).find("#username").val();
	var name = $(this).find("#name").val();
	var surname = $(this).find("#surname").val();
	var email = $(this).find("#email").val();
	var password = $(this).find("#password").val();
	var phone = $(this).find("#phoneNumber").val();
	console.log(registerformToJSON(username,name,surname,email,password,phone));
	$.ajax({
		method : 'POST',
		url : rootURL,
		contentType : 'application/json',
		dataType : "text",
		data : registerformToJSON(username,name,surname,email,password,phone),
		success : function(data) {
			window.location.href = "home.html";	
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("AJAX ERROR: " + errorThrown + XMLHttpRequest);
		}
	});
});

$(document).ready(function() {
	if($.cookie("stevan") != null && $.cookie("stevan") != undefined && $.cookie("stevan") != ""){
		window.location.href = "home.html";			
	}
});

function registerformToJSON(username,ime,prezime,email,password,telefon) {
	return JSON.stringify({
		"username" : username,
		"name" : ime,
		"surname" : prezime,
		"email" : email,
		"password" : password,
		"phoneNumber" : telefon,
	    "role": null,
	    "date": null
	});
}