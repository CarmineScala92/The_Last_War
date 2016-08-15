package progettoTpa;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class PunteggiDB {
	/**
	 * OK- FUNZIONA !Legge tutti i punteggi nel DB riguardanti un preciso livello di difficolta
	 */
	public static ArrayList<Punteggio> getPunteggiLivello(String livelloDifficolta)throws SQLException{
		String query = "select * from punteggio where Livello='"+livelloDifficolta+"' order by Score desc";
		ArrayList<Punteggio> punteggi = new ArrayList<Punteggio>();
		Connection con = DBConnectionPool.getConnection();
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(query);
		while (rs.next())
			punteggi.add(new Punteggio(rs.getString("Livello"), rs.getString("Nickname"), rs.getInt("CodicePunteggio"), rs.getInt("Score")));
		rs.close();
		con.commit();
		st.close();
		DBConnectionPool.releaseConnection(con);
		return punteggi;
	}
	
	
	
	/**
	 * OK FUNZIONA !Inserisce nuovo punteggio all'interno del DB
	 */
	public static boolean inserisciNuovoPunteggio(String nickname,int score,String livelloDifficolta)throws SQLException{
		Connection con = DBConnectionPool.getConnection();
		String query = "insert into `punteggio` (`CodicePunteggio`, `Nickname`, `Score`, `Livello`) values " + "(" + "NULL" + ", '" + nickname + "', '" + score + "', '" + livelloDifficolta + "') ";
		Statement st = con.createStatement();
		int risultato = st.executeUpdate(query); 
		con.commit();
		st.close();
		DBConnectionPool.releaseConnection(con);
		return (risultato) == 1;
	}
	
	
	
	/**
	 * FUNZIONA OK !!Ritorna il numero  di righe già esistenti riguardanti un preciso livello di difficolta
	 */
	public static int contaRigheConLivelloDifficolta(String livelloDifficolta)throws SQLException{
		String query = "select count(*) as 'NRighe' from punteggio where Livello='"+livelloDifficolta+"'";
		Connection con = DBConnectionPool.getConnection();
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(query);
		rs.next();
		int punteggioMinore=rs.getInt("NRighe");
		rs.close();
		con.commit();
		st.close();
		DBConnectionPool.releaseConnection(con);
		return punteggioMinore;
	}
	
	
	
	/**
	 * FUNZIONA OK!!! Ritorna l'id del punteggio minore riguardante uno specifico livello di difficolta
	 */
	public static int searchIdPunteggioMinore(String livelloDifficolta)throws SQLException{
		String query = "select CodicePunteggio from punteggio where Livello='"+livelloDifficolta+"'" + "AND Score=(select min(Score) from punteggio where Livello='"+ livelloDifficolta +"') ";
		Connection con = DBConnectionPool.getConnection();
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(query);
		rs.next();
		int IDpunteggioMinore=rs.getInt("CodicePunteggio");
		rs.close();
		con.commit();
		st.close();
		DBConnectionPool.releaseConnection(con);
		return IDpunteggioMinore;
	}
	
	
	
	/**
	 * FUNZIONA OK!! Ritorna il punteggio minore riguardante uno specifico livello di difficolta
	 */
	public static int returnPunteggioMinore(String livelloDifficolta)throws SQLException{
		String query = "select min(Score) as 'Score' from punteggio where Livello='"+livelloDifficolta+"'";
		Connection con = DBConnectionPool.getConnection();
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(query);
		rs.next();
		int punteggioMinore=rs.getInt("Score");
		rs.close();
		con.commit();
		st.close();
		DBConnectionPool.releaseConnection(con);
		return punteggioMinore;
	}
	
	
	/**
	 * FUNZIONA OK!! Elimina un punteggio all'interno del database
	 */
	public static boolean  deletePunteggio(int idPunteggio)throws SQLException{
		Connection con = DBConnectionPool.getConnection();
		String query = "delete from punteggio where CodicePunteggio =" + idPunteggio;
		Statement st = con.createStatement();
		int risultato = st.executeUpdate(query); 
		con.commit();
		st.close();
		DBConnectionPool.releaseConnection(con);
		return risultato == 1;
	}
	
}
