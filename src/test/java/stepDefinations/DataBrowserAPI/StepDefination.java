package stepDefinations.DataBrowserAPI;

import static io.restassured.RestAssured.given;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.Deserializers.Base;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.weavapi.utilities.APIResources;
import com.weavapi.utilities.ReadConfig;
import com.weavapi.utilities.Validation_method;

import static org.junit.Assert.*;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class StepDefination {
	
	
	String datasetid;
	 String versionid;
	
	Response response;
	JsonPath js;
	
	Map<String, Object> mapObject;
	
	  List<String> myList;
	  Validation_method vobj=new Validation_method();
	  
	  
	 
	
	
	
	@Given("the user has datasetid and versionid through {string}")
	public void the_user_has_datasetid_and_versionid(String resource) throws IOException {
		
		
		
		
		ReadConfig readconf1=new ReadConfig();
		RestAssured.baseURI=readconf1.getDataServiceURL();
		
		APIResources resourceAPI=APIResources.valueOf(resource);
		
		
		Response response1=given().log().all().header("Content-Type","application/json")
				.body(new String(Files.readAllBytes(Paths.get(System.getProperty("user.dir")+"/JSONFiles/add_by_path.json"))))
				.when().post(resourceAPI.getResource())
				.then().extract().response();
		
		String string_resp=response1.asString();
		System.out.println(string_resp);
		
		JsonPath js=new JsonPath(string_resp);
		
		datasetid=js.getString("data.dataset_id");
		versionid=js.getString("data.version_id");
		//System.out.println(datasetid);
		//System.out.println(versionid);
		
	    
	}
	
	@SuppressWarnings("unchecked")
	@When("the user calls {string} using request payload {string}")
	public void the_user_calls_api_get_col_stat_using_request(String Resource,String json_file) throws StreamReadException, DatabindException, IOException {
		
		//PrintStream log =new PrintStream(new FileOutputStream("logging.txt"));
		
		File jsonFile = new File(
				  System.getProperty("user.dir")+"/JSONFiles/"+json_file+"");
		
		
		//ObjectMapper mapper = new ObjectMapper();
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new Jdk8Module());
		
		
		  mapObject = mapper.readValue(jsonFile,new
		  TypeReference<Map<String, Object>>() {});
		  
		  if(mapObject.containsKey("dataset_id"))
		  {
		  
		  mapObject.put("dataset_id", datasetid);
		  
		 mapObject.put("version_id", versionid);
		  }
		  
		  else System.out.println("dataset_id not present");
		 
		 
		 

		  ReadConfig readconf=new ReadConfig();
		  RestAssured.baseURI=readconf.getDataBrowserURL();
		  
		  
		  APIResources resourceAPI=APIResources.valueOf(Resource);
			
			
			
	response=given().log().all().header("Content-Type","application/json")
	.body(mapObject)
	.when().post(resourceAPI.getResource())
	.then().extract().response();
	
	
	System.out.println("**********************************Resonse Data*************************************");
	String string_resp=response.asString();
	
	System.out.println(string_resp);
	js=new JsonPath(string_resp);
	
	
	
	   
	}
	
	
	public Map<String, Object> getMapObject() {
	      return mapObject;
	   }
	
	
	public JsonPath getJson() {
	      return js;
	   }
	
	
	@Given("the user provides no  datasetid and versionid")
	public void the_user_has_no_datasetid_and_versionid() {
	    // Write code here that turns the phrase above into concrete actions
	   
	}
	
	
	@Then("user should get status code {int}")
	public void user_should_get_status_code(int status_code) {
	    // Write code here that turns the phrase above into concrete actions
		
		//System.out.println("I am here2");
		//assertEquals(response.getStatusCode(),status_code);
		vobj.checkResponseCode(response, status_code);
	
	   
	}
	@Then("user should get status line as {string}")
	public void user_should_get_status_line_as(String status_line) {
	    // Write code here that turns the phrase above into concrete actions
		
		//assertEquals(response.getStatusLine(),status_line);
		vobj.checkStatusLine(response, status_line);
	   
	}
	
	
	
	@Then("response body should not be null")
	public void response_body_should_not_be_null() {
	    // Write code here that turns the phrase above into concrete actions
		
		//assertNotNull(response.getBody().asString());
		vobj.checkResponseBody(response);
	    
	}
	@Then("response time should be less than {int}")
	public void response_time_should_be_less_than(Integer response_time) {
	    // Write code here that turns the phrase above into concrete actions
		
		
		//assertTrue(response.getTime()<response_time);
		
		vobj.checkResponseTime(response, response_time);
	    
	}
	@Then("message in result should be {string}")
	public void message_in_result_should_be(String message) {
	    // Write code here that turns the phrase above into concrete actions
		
		assertEquals(js.get("status.result"),message);
		
		
	    
	}
	@Then("column names in API response data should be equal to column names given in input file")
	public void column_names_in_api_response_data_should_be_equal_to_column_names_given_in_input_file() {
		
		if(mapObject.containsKey("col_names"))
		{
		
		try {
			
			myList = (List<String>) mapObject.get("col_names");
			}
			
			catch(NullPointerException e) {
				System.out.println("NullPointerException thrown!");
			}
		
		Map<String, Integer> datamap = new HashMap<String, Integer>();
		
		try
		{
		datamap=(js.get("data."));
		}
		
		
		catch(NullPointerException e) {
			System.out.println("NullPointerException thrown!");
		}
		
		List<String> datacolumnname = new ArrayList<String>();
		for ( String key : datamap.keySet() ) 
		{
			datacolumnname.add(key);
			
			
		}
          
			
			  boolean boolval= datacolumnname.containsAll(myList);
			  assertTrue(boolval);
		}
		else System.out.println("col_names not present!!");
	    
	}
	
	
	
	


	

}
