package ms.forum.model;

import java.sql.Date;

public class Cookie {

	private String id;
	private int userId;
	private Date expirationDate;
	
	public Cookie() {
	}

	
	
	public Cookie(String id, int userId) {
		super();
		this.id = id;
		this.userId = userId;
		java.util.Date utilDate = new java.util.Date();
		this.expirationDate = new Date(utilDate.getTime() + 86400000 );
	}



	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}
}	
