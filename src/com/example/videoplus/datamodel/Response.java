package com.example.videoplus.datamodel;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Response {
	String videoID;
	String thumbnail;
	String name;
	
	
	public Response(String videoID, String thumbnail, String name) {
		
		this.videoID = videoID;
		this.thumbnail = thumbnail;
		this.name = name;
	}
	public String getVideoID() {
		return videoID;
	}
	public void setVideoID(String videoID) {
		this.videoID = videoID;
	}
	public String getThumbnail() {
		return thumbnail;
	}
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	
}
