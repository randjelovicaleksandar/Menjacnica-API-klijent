package menjacnicautility;
import java.util.LinkedList;
import menjacnica.Valuta;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class MenjacnicaUtility {

	public static JsonArray serijalizujValute(LinkedList<Valuta> valute) {
		JsonArray valuteArray = new JsonArray();
		
		for (int i = 0; i < valute.size(); i++) {
			Valuta v = valute.get(i);
			
			JsonObject valutaJson = new JsonObject();
			valutaJson.addProperty("naziv", v.getNaziv());
			valutaJson.addProperty("kurs", v.getKurs());
			
			valuteArray.add(valutaJson);
		}
		
		return valuteArray;
	}
	
	public static LinkedList<Valuta> rasclanjavanjeJsonKusneListe(JsonArray valuteJson) {
		LinkedList<Valuta> valute = new LinkedList<Valuta>();
		
		for (int i = 0; i < valuteJson.size(); i++) {
			JsonObject valuteJsonObjekat = (JsonObject) valuteJson.get(i);
			
			Valuta v = new Valuta();
			v.setNaziv(valuteJsonObjekat.get("naziv").getAsString());
			v.setKurs(valuteJsonObjekat.get("kurs").getAsDouble());
			
			valute.add(v);
		}
		
		return valute;
	}
	
}
