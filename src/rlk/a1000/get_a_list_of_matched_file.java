package rlk.a1000;
/**
 * This is an example of A1000
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
		// token 값 입력
		String token = "";
	    String address = "http://a1000.reversinglabs.io";
	    // hash 값 입력
		String hash = "";
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
