package com.ananda.sales.sms;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;

public class SendSms {
	
	private String username;
	private String password;
	private String message;
	private String type;
	private String dlr;
	private String destination;
	private String source;
	private String server;
	private int port;
	
//	public SendSms(String username, String password, String message, String type, String dlr, String destination,
//			String source, String server, int port) {
//		super();
//		this.username = username;
//		this.password = password;
//		this.message = message;
//		this.type = type;
//		this.dlr = dlr;
//		this.destination = destination;
//		this.source = source;
//		this.server = server;
//		this.port = port;
//	}
	
	public void submitMessage(String username, String password, String message, String type, String dlr, String destination,
			String source, String server, int port) {
		
		try {
			// Url that will be called to submit the message
			URL sendUrl = new URL("http://" + server + ":" + port
			+ "/bulksms/bulksms");
			HttpURLConnection httpConnection = (HttpURLConnection) sendUrl
			.openConnection();
			// This method sets the method type to POST so that
			// will be send as a POST request
			httpConnection.setRequestMethod("POST");
			// This method is set as true which we intend to send
			// input to the server
			httpConnection.setDoInput(true);
			// This method implies that we intend to receive data from server.
			httpConnection.setDoOutput(true);
			// Implies do not use cached data
			httpConnection.setUseCaches(false);
			// Data that will be sent over the stream to the server.
			DataOutputStream dataStreamToServer = new DataOutputStream(
			httpConnection.getOutputStream());
			
			dataStreamToServer.writeBytes("username="
					+ URLEncoder.encode(username, "UTF-8") + "&password="
					+ URLEncoder.encode(password, "UTF-8") + "&type="
					+ URLEncoder.encode(type, "UTF-8") + "&dlr="
					+ URLEncoder.encode(dlr, "UTF-8") + "&destination="
					+ URLEncoder.encode(destination, "UTF-8") + "&source="
					+ URLEncoder.encode(source, "UTF-8") + "&message="
					+ URLEncoder.encode(message, "UTF-8"));
					dataStreamToServer.flush();
					dataStreamToServer.close();
					// Here take the output value of the server.
					BufferedReader dataStreamFromUrl = new BufferedReader(
					new InputStreamReader(httpConnection.getInputStream()));
					String dataFromUrl = "", dataBuffer = "";
					// Writing information from the stream to the buffer
					while ((dataBuffer =
					dataStreamFromUrl.readLine()) != null) {
					dataFromUrl += dataBuffer;
					}
					/**
					* Now dataFromUrl variable contains the Response received from the
					* server so we can parse the response and process it accordingly.
					*/
					dataStreamFromUrl.close();
					//System.out.println("Response: " + dataFromUrl);
					} catch (Exception ex) {
					ex.printStackTrace();
					}
		
	}
	
	
	

}
