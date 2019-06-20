package rlk.a1000;

/**
 * This is an example of A1000
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.JSONObject;

public class upload_a_sample_to_a1000_for_analysis {

	public static void main(String[] args) {		
		run();		
	}	
	
	private static void run() {
		// token 값 입력
		String token = "";
	    String address = "http://a1000.reversinglabs.io";
	    String requestURL = address + "/api/uploads/";
	    // 업로드 파일 경로 입력
	    String fileLocation = "";
		
		HttpPost httpPost = new HttpPost (requestURL);
		httpPost.addHeader("Authorization", "Token " + token);
		
		File file = new File(fileLocation);
		ContentType contentType = ContentType.MULTIPART_FORM_DATA;
		FileBody payload = new FileBody(file, contentType, file.getName());
		
		// http body json 생성
		JSONObject jsonObject = new JSONObject();
        jsonObject.put("filename", "testFilename.extension");
        jsonObject.put("analysis", "cloud");
        jsonObject.put("tags", "testTag1,testTag2,testTag3");
        jsonObject.put("comment", "this is comment part");
        
        HttpClient httpClient = HttpClientBuilder.create().build();
        
		try {
			
			// http entity 설정
			HttpEntity entity = MultipartEntityBuilder.create().setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
					.addPart("file", payload)
					//.addPart("name", new StringBody("file"))					
					.addTextBody("data", jsonObject.toString(), ContentType.APPLICATION_JSON)
					.build();
			httpPost.setEntity(entity);
			
			HttpResponse httpClientResponse = httpClient.execute(httpPost);			
			
			if (httpClientResponse.getStatusLine().getStatusCode() != 201) {
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
