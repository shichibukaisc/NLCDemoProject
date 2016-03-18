package com.ibm.bluemix.demo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ibm.watson.developer_cloud.natural_language_classifier.v1.NaturalLanguageClassifier;
import com.ibm.watson.developer_cloud.natural_language_classifier.v1.model.Classification;

public class WatsonService {
	
	String username = "";
	String password = "";
	//TODO: Enter Classifier ID Here
	String classifierid = "XXXXXXXXX";
	
	
	public WatsonService() {
		setup();
	}

	private void setup() {
		
		String VCAP_SERVICES = System.getenv("VCAP_SERVICES");
        String Service_Name  = "natural_language_classifier";
 
        // Get the Service Credentials for DB2 SQL Database
        if (VCAP_SERVICES != null) {
            
            try {
            	JSONObject obj = new JSONObject(VCAP_SERVICES);
                JSONArray service = obj.getJSONArray(Service_Name);
 
                // retrieve the service information             
                JSONObject catalog = service.getJSONObject(0);
 
                // retrieve the credentials
                JSONObject credentials = catalog.getJSONObject("credentials");
 
                // get the credential contents
                username = credentials.getString("username");
                password = credentials.getString("password");
 
            } catch (NullPointerException | JSONException e) {          
                e.printStackTrace();      
            }
        }

	}
	
	public MessageResponse getClassLabel(String userInput) {
		NaturalLanguageClassifier service = new NaturalLanguageClassifier();
		service.setUsernameAndPassword(username,password);
		
		Classification classification = service.classify(classifierid, userInput);
		
		MessageResponse msg = new MessageResponse();
		msg.setUserInput(userInput);
		msg.setClassLabel(classification.getTopClass());
		msg.setConfidence(Double.toString(classification.getTopConfidence()*100));
		return msg;
	}
}
