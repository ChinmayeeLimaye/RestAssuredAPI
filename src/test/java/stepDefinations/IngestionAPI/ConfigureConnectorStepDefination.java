package stepDefinations.IngestionAPI;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.weavapi.utilities.APIResources;
import com.weavapi.utilities.ReadConfig;
import com.weavapi.utilities.Validation_method;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class ConfigureConnectorStepDefination {
	
	
	String workspace_id;
	
	Response response;
	JsonPath js;
	
	Map<String, Object> mapObject;
	
	Validation_method vobj=new Validation_method();
	
	static String connector_id;
	
	String connector_name_actual;
	
	@Given("the user has workspace_id through {string}")
	public void the_user_has_workspace_id_through(String resource) throws IOException {
		
		ReadConfig readconf1=new ReadConfig();
		RestAssured.baseURI=readconf1.getIngestionURL();
		APIResources resourceAPI=APIResources.valueOf(resource);
		
		Response response1=given().log().all().header("Content-Type","application/json")
				.body(new String(Files.readAllBytes(Paths.get(System.getProperty("user.dir")+"/JSONFiles/create_workspace.json"))))
				.when().post(resourceAPI.getResource())
				.then().extract().response();
		
		
		String string_resp=response1.asString();
		System.out.println(string_resp);
		
		JsonPath js=new JsonPath(string_resp);
		workspace_id=js.get("data.workspace_id");
		
		//System.out.println(workspace_id);
		
		
	   
	}
	@When("the user calls {string} using request payload {string}")
	public void the_user_calls_using_request_payload(String Resource, String json_file) throws StreamReadException, DatabindException, IOException {
		
		
		File jsonFile = new File(
				  System.getProperty("user.dir")+"/JSONFiles/"+json_file+"");
		
		
		//ObjectMapper mapper = new ObjectMapper();
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new Jdk8Module());
		
		
		  mapObject = mapper.readValue(jsonFile,new
		  TypeReference<Map<String, Object>>() {});
		  
		  if(mapObject.containsKey("workspace_id"))
		  {
		  
		  mapObject.put("workspace_id", workspace_id);
		  
		
		  }
		  
		 // else System.out.println("workspace_id not present");
		  
		  
		  if(mapObject.containsKey("connector_id"))
		  {
			  
			  
			  mapObject.put("connector_id", connector_id);
		  }
		 
		 
		 

		  ReadConfig readconf=new ReadConfig();
		  RestAssured.baseURI=readconf.getIngestionURL();
		  
		  
		  APIResources resourceAPI=APIResources.valueOf(Resource);
			
			
			
	response=given().log().all().header("Content-Type","application/json")
	.body(mapObject)
	.when().post(resourceAPI.getResource())
	.then().extract().response();
	
	
	System.out.println("**********************************Resonse Data*************************************");
	String string_resp=response.asString();
	
	System.out.println(string_resp);
	js=new JsonPath(string_resp);
	connector_id=js.get("data.connector_id");
	
	if(mapObject.containsKey("connector_name"))
	{
	connector_name_actual=(String) mapObject.get("connector_name");
			
	}
		
		
		
	    
	}
	@Then("Then user should get status code {int}")
	public void then_user_should_get_status_code(Integer status_code) {
		
		
		vobj.checkResponseCode(response, status_code);
	  
	}
	@Then("user should get status line as {string}")
	public void user_should_get_status_line_as(String status_line) {
		
		
		vobj.checkStatusLine(response, status_line);
	    
	}
	@Then("response body should not be null")
	public void response_body_should_not_be_null() {
		
		
		vobj.checkResponseBody(response);
	    
	}
	@Then("response time should be less than {int}")
	public void response_time_should_be_less_than(Integer response_time) {
		
		
		vobj.checkResponseTime(response, response_time);
	    
	}
	@Then("message in result should be {string}")
	public void message_in_result_should_be(String message) {
		
		
		assertEquals(js.get("status.result"),message);
	   
	}

	
	@Then("verify connector_id created maps to connector_name using {string}")
	public void verify_connector_id_created_maps_to_connector_name_using(String resource) {
	    // Write code here that turns the phrase above into concrete actions
		ReadConfig readconf1=new ReadConfig();
		RestAssured.baseURI=readconf1.getIngestionURL();
		
		APIResources resourceAPI=APIResources.valueOf(resource);
		
		Response response1=given().log().all().header("Content-Type","application/json")
				.body("{\n"
						+ "    \"connector_id\": \""+connector_id+"\"\n"
						+ "}")
				.when().post(resourceAPI.getResource())
				.then().extract().response();
		
		String string_resp=response1.asString();
		
		System.out.println(string_resp);
		JsonPath js1=new JsonPath(string_resp);
		
		assertEquals(connector_name_actual,js1.get("data.connector_name"));
		
		
	}
	
	
	
	@Given("the user has connector_id through {string}")
	public void the_user_has_connector_id_through(String string) {
	   

	}


}
