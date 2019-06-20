package rlk.a1000;
/**
 * This is an example of A1000
 * This code works
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

public class get_a_list_of_matched_file {

    
	public static void main(String[] args) {		
		run();		
	}
	
	private static void run() {
		String token = "c206375e5d058603e9766d31aa9dac023c9b4e8b";		
	    String address = "http://a1000.reversinglabs.io";		
		String hash = "d79422fa3626891e06ab394750625c2d461fe304";
		String requestURL = address + "/api/samples/" + hash + "/extracted-files/";		
				
		HttpClient httpClient = HttpClientBuilder.create().build();				

		try {
		
			HttpGet httpGet = new HttpGet(requestURL);			
			httpGet.addHeader("Content-Type", "application/json");						
			httpGet.addHeader("Authorization", "Token " + token);
			HttpResponse httpClientResponse = httpClient.execute(httpGet);
			System.out.println("token" + token);
			
			if (httpClientResponse.getStatusLine().getStatusCode() != 200) {
				throw new RuntimeException(
						"Failed : HTTP error code : " + httpClientResponse.getStatusLine().getStatusCode());
			}	
					

			BufferedReader br = new BufferedReader(
					new InputStreamReader(httpClientResponse.getEntity().getContent(), "utf-8"));
			String result = br.readLine();
			
			System.out.println(result.toString());
			
			httpClient.getConnectionManager().shutdown();
			
		} catch (Exception e) {
			e.printStackTrace();

		}
	}
}
