$(document).ready(function() {
	if($.cookie("stevan") != null && $.cookie("stevan") != undefined && $.cookie("stevan") != ""){
		$( "#loggedAs" ).show();
		$( "#logIn" ).hide();
		$( "#logOut" ).show();
		$( "#register" ).hide();
	}else{
		$( "#loggedAs" ).hide();
		$( "#logIn" ).show();
		$( "#logOut" ).hide();
		$( "#register" ).show();
	}
	$("#logOut").on("click",function() {
		$.cookie("stevan","");
	});
	$.ajax({
		method : 'POST',
		url : "../ms.forum/rest/cookie/user",
		contentType : 'application/json',
		success : function(data) {
			var d = data;
			$("#loggedAs").text("Logged as " + d.username);
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("JADOOOOO");
		}
		});
})