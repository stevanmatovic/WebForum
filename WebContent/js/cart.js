var shopping_cart_url = "../rs.ftn.jersey.webshop/rest/proizvodi/getJustSc";
var total_url = "../rs.ftn.jersey.webshop/rest/proizvodi/getTotal";
var clear_cart_url = "../rs.ftn.jersey.webshop/rest/proizvodi/clearSc";

findCart();
getTotal();

function findCart() {
	console.log('findCart');
	$.ajax({
		type : 'GET',
		url : shopping_cart_url,
		dataType : "json", // data type of response
		success : renderList
	});
}

function getTotal() {
	console.log('findAll');
	$.ajax({
		type : 'GET',
		url : total_url,
		dataType : "text", // data type of response
		success : function(data) {
			var total = $('#total');
			total.append('Ukupno : ' + data + 'dinara');
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("AJAX ERROR: " + errorThrown);
		}
	});
}

function renderList(data) {
	// JAX-RS serializes an empty list as null, and a 'collection of one' as an object (not an 'array of one')
	var list = data == null ? [] : (data instanceof Array ? data : [ data ]);
	
	$.each(list, function(index, product) {
        var tr = $('<tr></tr>');
        var total_price = product.count * product.product.price;
        tr.append('<td>' + product.product.name + '</td>' +
                '<td>' + product.product.price + '</td>' + 
                '<td>' + product.count + '</td>' + 
                '<td>' + total_price + '</td>' );
        $('#cartTable').append(tr);
	});
}

$(document).on('submit', '.clearForm', function(e) {
	e.preventDefault();
	console.log("remove products from cart");
	$.ajax({
		type : 'POST',
		url : clear_cart_url,
		dataType : "text",
		success : function(data) {
			$("#cartTable").find("tr:gt(0)").remove();
			$("#total").remove();
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("AJAX ERROR: " + errorThrown);
		}
	});
});
