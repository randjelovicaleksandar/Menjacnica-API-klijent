package menjacnica;

import java.net.URL;
import java.util.LinkedList;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;



public class JsonRatesAPIKomunikacija {

	private static final String appKey = "jr-75e13dcb061a2dea204964fae6c1c703";
	private static final String jsonRatesURL = "http://jsonrates.com/get/";
	
	public LinkedList<Valuta> vratiIznosKurseva(String[] valute) {
		LinkedList<Valuta> kursevi = new LinkedList<Valuta>();
		for (int i = 0; i < valute.length; i++) {
			try{
				URL url = new URL( jsonRatesURL + "?" +
					"from=" + valute[i] +
					"&to=RSD" +
					"&apiKey=" + appKey
				);
				
				String data = IOUtils.toString(url);
				JSONObject json = new JSONObject(data);
				Double rate = json.getDouble("rate");
				
				Valuta valuta = new Valuta();
				valuta.setNaziv(valute[i]);
				valuta.setKurs(rate);
				
				kursevi.add(i, valuta);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return kursevi;
	}
	
	//Proba da li metoda radi
	public static void main(String[] args) {
		JsonRatesAPIKomunikacija apiKomunikacija = new JsonRatesAPIKomunikacija();
		String[] valute = new String[2];
		valute[0]="EUR";
		valute[1]="USD";
		LinkedList<Valuta> kursevi = apiKomunikacija.vratiIznosKurseva(valute);
		for (int i = 0; i < kursevi.size(); i++) {
			System.out.println(kursevi.get(i));
		}
	}
}
