package com.ibm.bluemix.demo;

import javax.ws.rs.POST;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/jsonServices")
public class WebService {
	@POST
	@Path("/restaurantanswer")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces(MediaType.APPLICATION_JSON)
	public MessageResponse showRestaurants(UserInputParam param) {
		System.out.println(param.getMessage());
		
		MessageResponse resp =  evaluateAnswer(param.getMessage());
		
		DataService dataserv = new DataService();
		
		dataserv.saveMessages(resp);
		
		return resp;
	}
	
	protected MessageResponse evaluateAnswer(String message) {
		WatsonService watson = new WatsonService();
		MessageResponse msg = watson.getClassLabel(message);
		switch (msg.getClassLabel()) {
	        case "fastfood":
	        	msg.setMessage("There is McDonald near by. (Answer confidence: " + msg.getConfidence() + "%)");
	        	break;
	        case "breakfast":
	        	msg.setMessage("There is Cora that served great breakfast in the south side. (Answer confidence: " + msg.getConfidence() + "%)");
	            break;
	        case "dinner":
	        	msg.setMessage("I highly recommend Ruth's Chris Steak House. (Answer confidence: " + msg.getConfidence() + "%)");
	        	break;
		}
		return msg;
	}
}
