/**
 * A1000 API Examples
 *
 * Copyright (c) 2019 ReversingLabs Korea
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package rlk.a1000;

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
		// input token value
		String token = "";
		// input address value
	    String address = "";
	    String requestURL = address + "/api/uploads/";
		// input upload file path
	    String fileLocation = "";
		
		HttpPost httpPost = new HttpPost (requestURL);
		httpPost.addHeader("Authorization", "Token " + token);
		
		File file = new File(fileLocation);
		ContentType contentType = ContentType.MULTIPART_FORM_DATA;
		FileBody payload = new FileBody(file, contentType, file.getName());

		JSONObject jsonObject = new JSONObject();
        jsonObject.put("filename", "testFilename.extension");
        jsonObject.put("analysis", "cloud");
        jsonObject.put("tags", "testTag1,testTag2,testTag3");
        jsonObject.put("comment", "this is comment part");
        
        HttpClient httpClient = HttpClientBuilder.create().build();
        
		try {

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
