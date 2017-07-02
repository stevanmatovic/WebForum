package ms.forum.model;

public class Message {

	int id;
	int prima;
	int salje;
	String subject;
	String tekst;
	String senderName;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPrima() {
		return prima;
	}
	
	
	public String getSenderName() {
		return senderName;
	}
	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}
	public void setPrima(int prima) {
		this.prima = prima;
	}
	public int getSalje() {
		return salje;
	}
	public void setSalje(int salje) {
		this.salje = salje;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getTekst() {
		return tekst;
	}
	public void setTekst(String tekst) {
		this.tekst = tekst;
	}
	
}
