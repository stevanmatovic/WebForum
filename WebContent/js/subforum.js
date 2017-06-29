$(document).ready(function() {
	
	console.log(getUrlVars()["name"]);
	if(getUrlVars()["name"] == "" || getUrlVars()["name"] == null || getUrlVars()["name"] == undefined)
		window.location.href = "home.html";
	
	$.ajax({
		method : 'POST',
		url : "../ms.forum/rest/subforum",
		contentType : 'text/plain',
		data: getUrlVars()["name"],
		success : function(data) {
			var d = data;
			$("#name").text(d.name);
			$("#description").text(d.description);
			$("#rules").text(d.rules);
			
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			console.log(textStatus);
			console.log(errorThrown);
		}
	});
	
});



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