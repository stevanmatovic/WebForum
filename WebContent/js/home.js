$(document).ready(
		function() {
			if ($.cookie("stevan") != null && $.cookie("stevan") != undefined
					&& $.cookie("stevan") != "") {
				// KADA JE NEKO ULOGOVAN
				$("#loggedAs").show();
				$("#logIn").hide();
				$("#logOut").show();
				$("#register").hide();
				$("#createSubforum").hide();
			} else {
				// KADA NIKO NIJE LOGOVAN
				$("#loggedAs").hide();
				$("#logIn").show();
				$("#logOut").hide();
				$("#register").show();
				$("#createSubforum").hide();
			}

			$.ajax({
				method : 'POST',
				url : "../ms.forum/rest/cookie/user",
				contentType : 'application/json',
				success : function(data) {
					var d = data;
					$("#loggedAs").text("Logged as " + d.username);
					if (d.role == "admin") {
						$("#createSubforum").show();
					}
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					console.log(textStatus);
					console.log(errorThrown);
				}
			});

			$.ajax({
				method : 'POST',
				url : "../ms.forum/rest/subforum/menu",
				contentType : 'application/json',
				success : function(data) {
					data.forEach(function(element) {
							$( "#subforums" ).append( '<a class="list-group-item" href="/ms.forum/subforum.html?name=' + element.id +'">' + element.name + "</a>" );
						});
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					console.log(textStatus);
					console.log(errorThrown);
				}
			});
			
			$("#logOut").click(function() {

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
				location.reload();
			});

			$(".subforumLink").click(function() {
				alert('kliknuto');
			});
			
})

