package com.heidiaandahl.service;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.annotation.Generated;

@Generated("com.robohorse.robopojogenerator")
public class Response{

	@JsonProperty("Results")
	private Results results;

	@JsonProperty("responseTime")
	private int responseTime;

	@JsonProperty("message")
	private List<String> message;

	@JsonProperty("status")
	private String status;

	public void setResults(Results results){
		this.results = results;
	}

	public Results getResults(){
		return results;
	}

	public void setResponseTime(int responseTime){
		this.responseTime = responseTime;
	}

	public int getResponseTime(){
		return responseTime;
	}

	public void setMessage(List<String> message){
		this.message = message;
	}

	public List<String> getMessage(){
		return message;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"Response{" + 
			"results = '" + results + '\'' + 
			",responseTime = '" + responseTime + '\'' + 
			",message = '" + message + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}