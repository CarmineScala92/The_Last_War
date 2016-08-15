package progettoTpa;

public class Punteggio {
	String Livello;
	String Nickname;
	int Id;
	int score;
	public Punteggio(String liv,String nick,int idR,int scoreR){
		Livello=liv;
		Nickname=nick;
		Id=idR;
		score=scoreR;
	}
	public String getLivello() {
		return Livello;
	}
	public String getNickname() {
		return Nickname;
	}
	public int getId() {
		return Id;
	}
	public int getScore() {
		return score;
	}
}
