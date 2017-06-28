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
	
	$.ajax({
		method : 'POST',
		url : "../ms.forum/rest/cookie/user",
		contentType : 'application/json',
		success : function(data) {
			var d = data;
			$("#loggedAs").text("Logged as " + d.username);
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			console.log(textStatus);
			console.log(errorThrown);
		}
		});
	
	$( "#logOut" ).click(function() {
		  
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
		  $.cookie("stevan","");
		  location.reload();
		});
	
})

