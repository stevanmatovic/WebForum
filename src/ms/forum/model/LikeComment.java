package ms.forum.model;

public class LikeComment {

	int id;
	int idKomentar;
	int idUser;
	Boolean like;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdKomentar() {
		return idKomentar;
	}

	public void setIdKomentar(int idKomentar) {
		this.idKomentar = idKomentar;
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
