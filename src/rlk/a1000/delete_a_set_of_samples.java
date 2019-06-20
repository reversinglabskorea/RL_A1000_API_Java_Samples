package rlk.a1000;
/**
 * This is an example of A1000
 * This code works
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.HTTP;
import org.json.simple.JSONObject;

public class delete_a_set_of_samples {

	public static void main(String[] args) {		
		run();		
	}	
	
	private static void run() {
		String token = "c206375e5d058603e9766d31aa9dac023c9b4e8b";		
	    String address = "http://a1000.reversinglabs.io";
	    String requestURL = address + "/api/samples/delete_bulk/";	    	    

	    // 대상이되는 hash 값을 벌크로 받는 경우를 가정한 Array
	    ArrayList<String> example = new ArrayList<>();
	    example.add("03a5e4e873bdeb2a8c17abc41ded592654f89e35");
	    example.add("e7fe1a4c5232d7e449ca96bb398a9bbea6032ea3");
		
		// json value에 포함이될 checkSum을 ArrayList로 할당
		ArrayList<String> checkSum = new ArrayList<>();		 
		for(int i = 0; i < example.size(); i++) {
			checkSum.add(example.get(i));			 	 
		}
		
		// http body json 생성
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("hash_value", checkSum);
		
		System.out.println(jsonObject.toString());
		
		// http entity 설정 
		StringEntity entity = new StringEntity(jsonObject.toString(), "utf-8");
		entity.setContentType("application/json");
		
		// http post 설정
		HttpPost httpPost = new HttpPost (requestURL);
		httpPost.addHeader("Authorization", "Token " + token);
		httpPost.setEntity(entity);
		
		HttpClient httpClient = HttpClientBuilder.create().build();
	    
		try {
			
			// 실행
			HttpResponse httpClientResponse = httpClient.execute(httpPost);			
			
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

