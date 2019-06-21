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
		// input token value
		String token = "";
		// input address value
	    String address = "";
	    String requestURL = address + "/api/samples/delete_bulk/";	    	    

	    // input hash values
	    ArrayList<String> example = new ArrayList<>();
	    example.add("");
	    example.add("");

		ArrayList<String> checkSum = new ArrayList<>();		 
		for(int i = 0; i < example.size(); i++) {
			checkSum.add(example.get(i));			 	 
		}

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("hash_value", checkSum);
		
		System.out.println(jsonObject.toString());

		StringEntity entity = new StringEntity(jsonObject.toString(), "utf-8");
		entity.setContentType("application/json");

		HttpPost httpPost = new HttpPost (requestURL);
		httpPost.addHeader("Authorization", "Token " + token);
		httpPost.setEntity(entity);
		
		HttpClient httpClient = HttpClientBuilder.create().build();
	    
		try {

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

