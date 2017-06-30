package ms.forum.model;

import java.sql.Date;

public class Post {

	int id;
	int subforumId;
	String title;
	String type;
	int autorId;
	String tekst;
	String link;
	String picture;
	Date date;
	

	public Post() {
	}

	




	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getSubforumId() {
		return subforumId;
	}

	public void setSubforumId(int subforumId) {
		this.subforumId = subforumId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	private String help;
	
	public String getHelp() {
		return help;
	}

	public void setHelp(String help) {
		this.help = help;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getAutorId() {
		return autorId;
	}

	public void setAutorId(int autorId) {
		this.autorId = autorId;
	}

	public String getTekst() {
		return tekst;
	}

	public void setTekst(String tekst) {
		this.tekst = tekst;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	
}
