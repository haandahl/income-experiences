package com.heidiaandahl.service;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.annotation.Generated;

@Generated("com.robohorse.robopojogenerator")
public class DataItem{

	@JsonProperty("period")
	private String period;

	@JsonProperty("year")
	private String year;

	@JsonProperty("periodName")
	private String periodName;

	@JsonProperty("value")
	private String value;

	@JsonProperty("footnotes")
	private List<FootnotesItem> footnotes;

	@JsonProperty("latest")
	private String latest;

	public void setPeriod(String period){
		this.period = period;
	}

	public String getPeriod(){
		return period;
	}

	public void setYear(String year){
		this.year = year;
	}

	public String getYear(){
		return year;
	}

	public void setPeriodName(String periodName){
		this.periodName = periodName;
	}

	public String getPeriodName(){
		return periodName;
	}

	public void setValue(String value){
		this.value = value;
	}

	public String getValue(){
		return value;
	}

	public void setFootnotes(List<FootnotesItem> footnotes){
		this.footnotes = footnotes;
	}

	public List<FootnotesItem> getFootnotes(){
		return footnotes;
	}

	public void setLatest(String latest){
		this.latest = latest;
	}

	public String getLatest(){
		return latest;
	}

	@Override
 	public String toString(){
		return 
			"DataItem{" + 
			"period = '" + period + '\'' + 
			",year = '" + year + '\'' + 
			",periodName = '" + periodName + '\'' + 
			",value = '" + value + '\'' + 
			",footnotes = '" + footnotes + '\'' + 
			",latest = '" + latest + '\'' + 
			"}";
		}
}