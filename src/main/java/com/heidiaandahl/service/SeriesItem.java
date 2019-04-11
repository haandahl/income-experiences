package com.heidiaandahl.service;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.annotation.Generated;

@Generated("com.robohorse.robopojogenerator")
public class SeriesItem{

	@JsonProperty("data")
	private List<DataItem> data;

	@JsonProperty("seriesID")
	private String seriesID;

	public void setData(List<DataItem> data){
		this.data = data;
	}

	public List<DataItem> getData(){
		return data;
	}

	public void setSeriesID(String seriesID){
		this.seriesID = seriesID;
	}

	public String getSeriesID(){
		return seriesID;
	}

	@Override
 	public String toString(){
		return 
			"SeriesItem{" + 
			"data = '" + data + '\'' + 
			",seriesID = '" + seriesID + '\'' + 
			"}";
		}
}