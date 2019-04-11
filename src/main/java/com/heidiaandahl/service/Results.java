package com.heidiaandahl.service;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.annotation.Generated;

@Generated("com.robohorse.robopojogenerator")
public class Results{

	@JsonProperty("series")
	private List<SeriesItem> series;

	public void setSeries(List<SeriesItem> series){
		this.series = series;
	}

	public List<SeriesItem> getSeries(){
		return series;
	}

	@Override
 	public String toString(){
		return 
			"Results{" + 
			"series = '" + series + '\'' + 
			"}";
		}
}