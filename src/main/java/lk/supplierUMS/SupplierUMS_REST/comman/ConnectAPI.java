package lk.supplierUMS.SupplierUMS_REST.comman;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Component;



@Component
public class ConnectAPI {
	
	public HttpURLConnection getToken(){
		HttpURLConnection connection = null;
		URL tokenurl = null;
		try {
			tokenurl = new URL("http://supplerportal.cargillsceylon.com/api/GetToken");
			connection = (HttpURLConnection) tokenurl.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setUseCaches(false);
			connection.setDoOutput(true);
			
			
			OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
			JSONObject json = new JSONObject();
			json.put("UserName", "Askpi");
			json.put("Password", "Askpi@123");
			out.write(json.toString());
			out.flush();
			out.close();

			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return connection;
		
	}

	public HttpURLConnection getDataFromAPI(String apiURL, JSONObject data) {
		
		URL dataurl;
		HttpURLConnection dataConnection = null;
		try {
			
			BufferedReader br = null;
			StringBuilder sb = new StringBuilder();
			String output = "";
			
			HttpURLConnection connection = getToken();
			
			if (200 <= connection.getResponseCode() && connection.getResponseCode() <= 299) {
				System.out.println("Inside 200");
				
				br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				sb.append(output);
				//System.out.println(br.toString());
			}else{
				System.out.println("Inside ELSE");
				br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			}
			
			if (connection.getResponseCode()==200) {
				while ((output = br.readLine()) != null) {
					sb.append(output);
				}
				System.out.println(sb.toString());
				
				dataurl = new URL(apiURL);
				
				System.out.println("011");

				dataConnection = (HttpURLConnection) dataurl.openConnection();
				dataConnection.setRequestMethod("POST");
				dataConnection.setRequestProperty("Content-Type", "application/json");
				dataConnection.setUseCaches(false);
				dataConnection.setDoOutput(true);
				System.out.println("022");
				OutputStreamWriter out = new OutputStreamWriter(dataConnection.getOutputStream());
				System.out.println("022");
				
				JSONObject json = (JSONObject) new JSONParser().parse(sb.toString());
				String token = json.get("token").toString();
				System.out.println("Received JSON token=====" + token);
				data.put("token", token);
				out.write(data.toString());
				out.flush();
				out.close();
				
				if (200 <= dataConnection.getResponseCode() && dataConnection.getResponseCode() <= 299) {
					System.out.println("Inside 200");
					
					br = new BufferedReader(new InputStreamReader(dataConnection.getInputStream()));
					sb.append(output);
					//System.out.println(br.toString());
				}else{
					System.out.println("Inside ELSE");
					br = new BufferedReader(new InputStreamReader(dataConnection.getInputStream()));
				}
			}
			

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return dataConnection;

	}
	
public HttpURLConnection sendFCMRequest(String uRL, JSONObject data) {
		
		URL dataurl;
		HttpURLConnection dataConnection = null;
		try {
			
			BufferedReader br = null;
			StringBuilder sb = new StringBuilder();
			String output = "";
			
			dataurl = new URL(uRL);
			
			dataConnection = (HttpURLConnection) dataurl.openConnection();
			dataConnection.setRequestMethod("POST");
			dataConnection.setRequestProperty("Content-Type", "application/json");
			dataConnection.setRequestProperty("Authorization", "key=AIzaSyCCoTlAIRA_nl528YLIk8Uo7jZPc5_6ZZ4");
			dataConnection.setUseCaches(false);
			dataConnection.setDoOutput(true);
			
			OutputStreamWriter out = new OutputStreamWriter(dataConnection.getOutputStream());
			
			System.out.println("Received JSON data=====" + data);
			
			out.write(data.toString());
			out.flush();
			out.close();
			
			if (200 <= dataConnection.getResponseCode() && dataConnection.getResponseCode() <= 299) {
				System.out.println("Inside 200");
				
				br = new BufferedReader(new InputStreamReader(dataConnection.getInputStream()));
				sb.append(output);
//				System.out.println(output);
			}else{
				System.out.println("Inside ELSE");
				br = new BufferedReader(new InputStreamReader(dataConnection.getInputStream()));
			}
			if (dataConnection.getResponseCode()==200) {
				while ((output = br.readLine()) != null) {
					sb.append(output);
				}
				System.out.println(output);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return dataConnection;

	}
}
