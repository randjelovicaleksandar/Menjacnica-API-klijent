package menjacnica;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

public class JsonRatesAPIKomunikacija {

	private static final String appKey = "jr-75e13dcb061a2dea204964fae6c1c703";
	private static final String jsonRatesURL = "http://jsonrates.com/get/";
	
	public LinkedList<Valuta> vratiIznosKurseva(String[] valute) {
		LinkedList<Valuta> kursevi = new LinkedList<Valuta>();
		for (int i = 0; i < valute.length; i++) {
			String url = jsonRatesURL + "?" +
					"from=" + valute[i] +
					"&to=RSD"+
					"&apiKey=" + appKey;
			try{
				String result = sendGet(url);
				
				Gson gson = new GsonBuilder().create();
				JsonObject jsonResult = gson.fromJson(result, JsonObject.class);
				double rate = Double.parseDouble(jsonResult.get("rate").getAsString());
				
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
	
	private String sendGet(String url) throws IOException {
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		
		con.setRequestMethod("GET");
		
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		
		boolean endReading = false;
		String response = "";
		
		while (!endReading) {
			String s = in.readLine();
			
			if (s != null) {
				response += s;
			} else {
				endReading = true;
			}
		}
		in.close();
 
		return response.toString();
	}
	
	//Proba da li metoda radi
	public static void main(String[] args) {
		JsonRatesAPIKomunikacija apiKomunikacija = new JsonRatesAPIKomunikacija();
		String[] valute = new String[4];
		valute[0]="EUR";
		valute[1]="USD";
		valute[2]="CAD";
		valute[3]="HRK";
		LinkedList<Valuta> kursevi = apiKomunikacija.vratiIznosKurseva(valute);
		for (int i = 0; i < kursevi.size(); i++) {
			System.out.println(kursevi.get(i));
		}
	}
}
