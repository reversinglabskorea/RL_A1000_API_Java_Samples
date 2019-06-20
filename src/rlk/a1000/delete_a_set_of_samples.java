package rlk.a1000;
/**
 * This is an example of A1000
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.JSONObject;

public class delete_a_set_of_samples {

	public static void main(String[] args) {		
		run();		
	}	
	
	private static void run() {
		// token 값 입력
		String token = "";
	    String address = "http://a1000.reversinglabs.io";
	    String requestURL = address + "/api/samples/delete_bulk/";	    	    

	    // 대상이되는 hash 값을 벌크로 받는 경우를 가정한 Array
		// hash 값 입력
	    ArrayList<String> example = new ArrayList<>();
	    example.add("");
	    example.add("");
		
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

