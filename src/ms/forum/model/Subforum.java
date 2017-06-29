package ms.forum.model;

public class Subforum {
	
	int id;
	
	String name;

	String description;
	
	String rules;

	int moderatorId;

	
	
	public Subforum() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}



	public int getModeratorId() {
		return moderatorId;
	}

	public void setModeratorId(int moderatorId) {
		this.moderatorId = moderatorId;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRules() {
		return rules;
	}

	public void setRules(String rules) {
		this.rules = rules;
	}

	
	
}
