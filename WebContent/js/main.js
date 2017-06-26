/**
 * 
 */

var rootURL = "../rs.ftn.jersey.webshop/rest/proizvodi/add";
var rootURL2 = "../rs.ftn.jersey.webshop/rest/proizvodi/getJustProducts";

findAll();

$(document).on('submit', '.forma', function(e) {
	e.preventDefault();
	console.log("add product");
	var data = $('#forma').serialize();
	//alert(data);
	//alert(formToJSON())
	$.ajax({
		type : 'POST',
		url : rootURL,
		contentType : 'application/json',
		dataType : "json",
		data : formToJSON(),
		success : function(data) {
			alert(data);
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("AJAX ERROR: " + errorThrown);
		}
	});
});

function findAll() {
	console.log('findAll');
	$.ajax({
		type : 'GET',
		url : rootURL2,
		dataType : "json", // data type of response
		success : renderList
	});
}

function renderList(data) {
	// JAX-RS serializes an empty list as null, and a 'collection of one' as an object (not an 'array of one')
	var list = data == null ? [] : (data instanceof Array ? data : [ data ]);
	var forma = $("#productsform");
	
//	$('#wineList li').remove();
//	$.each(list, function(index, product) {
//		$('#wineList').append(
//				'<li><a href="#" data-identity="' + product.name + '">'
//						+ product.price + "," + product.name + '</a></li>');
//	});
	$.each(list, function(index, product) {
        var forma = $('<form method="get" class="productsform" action="ShoppingCartServlet"></form>');
        var tr = $('<tr></tr>');
        tr.append('<td>' + product.name + '</td>' +
                '<td>' + product.price + '</td>');
        forma.append('<input type="text" size="3" name="count">' +
                '<input type="hidden" name="id" value='+ product.id +'>' +
                '<input type="submit" value="Dodaj">');
        var td = $('<td></td>');
        td.append(forma);
        tr.append(td);
        $('#tabela').append(tr);
	});
}

$(document).on('submit', '.productsform', function(e) {
	e.preventDefault();
	console.log("add product");
//	var data = $('.productsform').serialize();
	var id = $(this).find("input[type=hidden]").val();
	var count = $(this).find("input").val();
	$.ajax({
		type : 'POST',
		url : rootURL,
		contentType : 'application/json',
		dataType : "text",
		data : formToJSON(id, count),
		success : function(data) {
			toastr.options = {
					  "closeButton": true,
					  "debug": false,
					  "newestOnTop": false,
					  "progressBar": false,
					  "positionClass": "toast-top-right",
					  "preventDuplicates": false,
					  "onclick": null,
					  "showDuration": "300",
					  "hideDuration": "1000",
					  "timeOut": "5000",
					  "extendedTimeOut": "1000",
					  "showEasing": "swing",
					  "hideEasing": "linear",
					  "showMethod": "fadeIn",
					  "hideMethod": "fadeOut"
					}
			toastr.info('Proizvod [ ' + data + '] uspesno dodat u korpu.');

		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("AJAX ERROR: " + errorThrown);
		}
	});
});

//Helper function to serialize all the form fields into a JSON string
function formToJSON(id, count) {
	return JSON.stringify({
		"id" : id,
		"count" : count,
	});
}