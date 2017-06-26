package ms.forum;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Path("/HelloWorld")
public class HelloWorld {

	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Korisnik test(){
		Korisnik k = new Korisnik();
		k.setIme("Pera");
		k.setPrezime("Peric");
		return k;
	}
	
}
