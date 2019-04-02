package com.java.model;

import java.sql.Timestamp;

public class Event {
	
	String event_type;
	String data;
	Timestamp timestamp;
	
	Event() {
		super();
	}
	public Event(String event_type, String data, Timestamp timestamp) {
		super();
		this.event_type = event_type;
		this.data = data;
		this.timestamp = timestamp;
	}
	public String getEvent_type() {
		return event_type;
	}
	public void setEvent_type(String event_type) {
		this.event_type = event_type;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public Timestamp getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
	

}

