package com.java.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.java.model.Event;

import reactor.core.publisher.Flux;

@RestController
public class StatisticsController {
    Logger logger = LoggerFactory.getLogger(StatisticsController.class);
	@GetMapping(value = "/statistics", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> getTemperature() {
    	String file = "C:\\generator-windows-amd64.exe";
		Process p = null;
		try {
			p = Runtime.getRuntime().exec(file);
		} catch (IOException e) {
			logger.info(e.getMessage());
			
		}
		
		BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Integer> wordMap = new HashMap<>();	
		Map<String, Integer> eventMap = new HashMap<>();
		
        return Flux.fromStream(Stream.generate(() -> {
			try {
				return input.readLine();
			} catch (IOException e) {
				logger.info(e.getMessage());
			}
			return null;
		})
            .map(s -> s)
            .peek((line) -> {
            	
                logger.info(line);
            }))
            .map(s -> {
            	
				
            	Event event; 
				try {
					event = mapper.readValue(s, Event.class);
					
					mapUpdate(eventMap, event.getEvent_type());
					mapUpdate(wordMap, event.getData());
				
				
				} catch (JsonParseException e) {
					logger.info(e.getMessage());
				} catch (JsonMappingException e) {
					logger.info(e.getMessage());
				} catch (IOException e) {
					logger.info(e.getMessage());
				}
				return " {eventType: "+eventMap.toString() +", words: "+ wordMap.toString()+" }";
            	
            })
            .delayElements(Duration.ofSeconds(1));
    }
	private void mapUpdate(Map<String, Integer> map, String data) {
		int counter = 0;
		
		if(map.containsKey(data))
		{
			counter = map.get(data);
		}
		map.put(data, counter+1);
	}
}
