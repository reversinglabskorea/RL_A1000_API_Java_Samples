package rlk.a1000;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
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
import org.json.simple.JSONObject;

public class retrieve_a_summary_analysis_report {

	public static void main(String[] args) {		
		run();		
	}	
	
	private static void run() {
		String token = "c206375e5d058603e9766d31aa9dac023c9b4e8b";		
	    String address = "http://a1000.reversinglabs.io";
	    String requestURL = address + "/api/samples/list/";

	    // 대상이되는 hash 값을 벌크로 받는 경우를 가정한 Array
	    ArrayList<String> example = new ArrayList<>();
	    example.add("ac88ecc39661fa1bdafca53eb2db02fab3fc7b41");
	    example.add("ac88ecc39661fa1bdafca53eb2db02fab3fc7b41");
		
		// json value에 포함이될 checkSum을 ArrayList로 할당
		ArrayList<String> checkSum = new ArrayList<>();		 
		for(int i = 0; i < example.size(); i++) {
			checkSum.add(example.get(i));			 	 
		}
		
		// 대상이 되는 fields 지정
		ArrayList<String> fields = new ArrayList<>();
		fields.add("threat_status");
		fields.add("threat_level");
		fields.add("threat_name");
		fields.add("trust_factor");
		fields.add("classification_origin");
		fields.add("classification_reason");
		fields.add("local_first_seen");
		fields.add("local_last_seen");
		
		// http body json 생성
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("hash_value", checkSum);
		jsonObject.put("fields", fields);
		
		System.out.println(jsonObject.toJSONString());
		
		// http entity 설정 
		//StringEntity entity = new StringEntity(jsonObject.toString(), "utf-8");
		//entity.setContentType("application/json");
		
					
		// http post 설정
		HttpPost httpPost = new HttpPost (requestURL);
		httpPost.addHeader("Authorization", "Token " + token);		
		//httpPost.setEntity(entity);		
		
		HttpClient httpClient = HttpClientBuilder.create().build();
		
		try {			
			HttpEntity entity = MultipartEntityBuilder.create().setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
					.build();
			httpPost.setEntity(entity);
			
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

