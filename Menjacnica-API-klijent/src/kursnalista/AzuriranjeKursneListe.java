package kursnalista;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.GregorianCalendar;
import java.util.LinkedList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import menjacnica.JsonRatesAPIKomunikacija;
import menjacnica.Valuta;
import menjacnicautility.MenjacnicaUtility;

public class AzuriranjeKursneListe {

	private static final String putanjaDoFajlaKursnaLista = "src/kursnaLista.json";
	
	public LinkedList<Valuta> ucitajValute() {
		LinkedList<Valuta> kursnaListaZaVracanje = new LinkedList<Valuta>();
		try {
			FileReader reader = new FileReader(putanjaDoFajlaKursnaLista);
			Gson gson = new GsonBuilder().create();
			JsonArray kursnaLista = gson.fromJson(reader, JsonArray.class);
			kursnaListaZaVracanje = MenjacnicaUtility.rasclanjavanjeJsonKusneListe(kursnaLista);
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return kursnaListaZaVracanje;
	}
	
	public void upisiValute(LinkedList<Valuta> valute, GregorianCalendar calendar) {
		try {
			calendar = new GregorianCalendar();
			FileWriter out = new FileWriter(putanjaDoFajlaKursnaLista);
			
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			JsonObject datum = new JsonObject();
			datum.addProperty("datum", calendar.toString());
			out.write(gson.toJson(datum));
			
			JsonArray kursnaListaJson = MenjacnicaUtility.serijalizujValute(valute);
			String azuriranaKursanaLista = gson.toJson(kursnaListaJson);
			out.write(azuriranaKursanaLista);
			
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void azurirajValute() {
		LinkedList<Valuta> kursnaLista = ucitajValute();
		String[] valute = new String[kursnaLista.size()];
		for (int i = 0; i < kursnaLista.size(); i++) {
			valute[i] = kursnaLista.get(i).getNaziv();
		}
		JsonRatesAPIKomunikacija apiKomunikacija = new JsonRatesAPIKomunikacija();
		kursnaLista = apiKomunikacija.vratiIznosKurseva(valute);
		GregorianCalendar trenutniDatum = new GregorianCalendar();
 		upisiValute(kursnaLista, trenutniDatum);
	}
}
