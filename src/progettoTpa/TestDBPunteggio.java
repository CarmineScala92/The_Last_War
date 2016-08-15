package progettoTpa;

import java.sql.SQLException;
import java.util.ArrayList;

public class TestDBPunteggio {
	public static void main(String[] args) throws SQLException{
		ArrayList<Punteggio> arrayPunteggi=PunteggiDB.getPunteggiLivello("Facile");
		for (int i = 0; i < arrayPunteggi.size(); i++) {
			System.out.println("ID: "+arrayPunteggi.get(i).getId() + " NICK: " +arrayPunteggi.get(i).getNickname() + " PUNT: " +arrayPunteggi.get(i).getScore() + " LIV: "+arrayPunteggi.get(i).getLivello() );
		}
		
		//if(db.inserisciNuovoPunteggio("CARMINE", 23, "Facile")){
		//	System.out.println("PUNTEGGIO INSERITO");
		//}else{
		//	System.out.println("PUNTEGGIO NON INSERITO");
		//}
		
		
		String livello="Difficile";
		System.out.println("Righe "+livello+": " + PunteggiDB.contaRigheConLivelloDifficolta(livello));
		
		//if(PunteggiDB.deletePunteggio(10)){ // AL POSTO DEL DIECI DOVRAI METTERE ID INSERITO SOPRA
		//	System.out.println("punteggio cancellato");
		//}else{
		//	System.out.println("punteggio non cancellato");
		//}
		
		String livDiff="Medio";
		System.out.println("PUNTEGGIO MINORE: " +PunteggiDB.returnPunteggioMinore(livDiff));
		
		System.out.println("ID PUNTEGGIO MINORE: " +PunteggiDB.searchIdPunteggioMinore(livDiff));

	}
}
