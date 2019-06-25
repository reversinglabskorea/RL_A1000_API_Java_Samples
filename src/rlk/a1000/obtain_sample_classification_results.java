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

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

public class obtain_sample_classification_results {

    public static void main(String[] args) {
        run();
    }

    private static void run() {
        // input token value
        String token = "";
        // input address value
        String address = "";
        // input hash value
        String hash = "";
        String requestURL = address + "/api/samples/" + hash + "/classification/";

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
