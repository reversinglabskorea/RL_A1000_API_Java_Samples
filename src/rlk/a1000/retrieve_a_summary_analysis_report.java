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

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.JSONObject;

public class retrieve_a_summary_analysis_report {

	public static void main(String[] args) {		
		run();		
	}	
	
	private static void run() {
		// input token value
		String token = "c206375e5d058603e9766d31aa9dac023c9b4e8b";
		// input address value
	    String address = "http://a1000.reversinglabs.io";
	    String requestURL = address + "/api/samples/list/";

	    // input hash values
	    ArrayList<String> example = new ArrayList<>();
	    example.add("03a5e4e873bdeb2a8c17abc41ded592654f89e35");
	    example.add("5e9e6bc8dd0f500e2a303225bf2c03ff886fa655");
		
		ArrayList<String> checkSum = new ArrayList<>();
		for(int i = 0; i < example.size(); i++) {
			checkSum.add(example.get(i));			 	 
		}
		
		ArrayList<String> fields = new ArrayList<>();
		fields.add("threat_status");
		fields.add("threat_level");
		fields.add("threat_name");
		fields.add("trust_factor");
		fields.add("classification_origin");
		fields.add("classification_reason");
		fields.add("local_first_seen");
		fields.add("local_last_seen");
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("hash_value", checkSum);
		jsonObject.put("fields", fields);
		
		System.out.println(jsonObject.toJSONString());
		

		HttpPost httpPost = new HttpPost (requestURL);
		httpPost.addHeader("Authorization", "Token " + token);
		
		HttpClient httpClient = HttpClientBuilder.create().build();
		
		try {			
			HttpEntity entity = MultipartEntityBuilder.create().setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
					.build();
			httpPost.setEntity(entity);

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

