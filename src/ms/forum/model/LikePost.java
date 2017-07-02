package ms.forum.model;

public class LikePost {
	private int id;
	private int idTema;
	private int idUser;
	private Boolean like;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdTema() {
		return idTema;
	}

	public void setIdTema(int idTEma) {
		this.idTema = idTEma;
	}

	public int getIdUser() {
		return idUser;
	}

	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}

	public Boolean getLike() {
		return like;
	}

	public void setLike(Boolean like) {
		this.like = like;
	}

}
