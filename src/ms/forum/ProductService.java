package ms.forum;


import java.util.Collection;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

@Path("/proizvodi")
public class ProductService {

	@Context
	HttpServletRequest request;
	@Context
	ServletContext ctx;

	@GET
	@Path("/test")
	@Produces(MediaType.TEXT_PLAIN)
	public String test() {
		return "Hello Jersey";
	}
	
	@GET
	@Path("/hello")
	@Produces(MediaType.TEXT_HTML)
	public String htmlTest() {
		return "<html> " + "<title>" + "Hello Jersey" + "</title>"
		        + "<body><h1>" + "Hello Jersey" + "</body></h1>" + "</html> ";
	}

	@GET
	@Path("/getJustProducts")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Product> getJustProducts() {
		System.out.println("get products");
		return getProducts().getValues();
	}
	
	@POST
	@Path("/add")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
//    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces(MediaType.TEXT_PLAIN)
	public String add(ProductToAdd p) {
		for (Product pr : getProducts().getProductList()) {
			System.out.println(pr.getName() + " " + pr.getPrice());
		}
		getShoppingCart().addItem(getProducts().getProduct(p.id), new Integer(p.count));
		System.out.println("Product " + getProducts().getProduct(p.id)
				+ " added with count: " + p.count);
		return getProducts().getProduct(p.id).getName();
	}

	@GET
	@Path("/getJustSc")
	@Produces(MediaType.APPLICATION_JSON)
	public List<ShoppingCartItem> getJustSc() {
		return getShoppingCart().getItems();
	}
	
	@GET
	@Path("/getTotal")
	@Produces(MediaType.TEXT_PLAIN)
	public String getTotal() {
		return "" + getShoppingCart().getTotal();
	}

	@POST
	@Path("/clearSc")
	@Produces(MediaType.TEXT_PLAIN)
	public String clearSc() {
		getShoppingCart().getItems().clear();
		return "OK";
	}

	private Products getProducts() {
		Products products = (Products) ctx.getAttribute("products");
		if (products == null) {
			products = new Products(ctx.getRealPath(""));
			ctx.setAttribute("products", products);
		} 
		return products;
	}
	
	private ShoppingCart getShoppingCart() {
		ShoppingCart sc = (ShoppingCart) request.getSession().getAttribute("shoppingCart");
		if (sc == null) {
			sc = new ShoppingCart();
			request.getSession().setAttribute("shoppingCart", sc);
		} 
		return sc;
	}
	

}
