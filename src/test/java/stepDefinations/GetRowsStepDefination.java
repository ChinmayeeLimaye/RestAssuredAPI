package stepDefinations;

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
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.weavapi.utilities.APIResources;
import com.weavapi.utilities.Check_sorted_columns;
import com.weavapi.utilities.ReadConfig;
import com.weavapi.utilities.Validation_method;

import static org.junit.Assert.*;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class GetRowsStepDefination{
	
	
	

	
	//Response response;
	JsonPath js;
	
	Map<String, Object> mapObject1;
	
	  List<String> myList;
	  
	  private StepDefination StepDefinationobj;
	  
	  String filename;
	  
	  int rowcount;
	  
	  List<String> operation_col_name;
	  
	  List<Map<String,Object>> operationobj;
	  
	  
	  

	  public GetRowsStepDefination(StepDefination StepDefinationobj)
	  {
		  this.StepDefinationobj =StepDefinationobj;
		  mapObject1=StepDefinationobj.getMapObject();
		  js=StepDefinationobj.getJson();
		  
	  }
	  
	  
	
	
		/*
		 * @Given("the user has datasetid and versionid through {string}") public void
		 * the_user_has_datasetid_and_versionid(String resource) throws IOException { //
		 * Write code here that turns the phrase above into concrete actions
		 * 
		 * ReadConfig readconf1=new ReadConfig();
		 * RestAssured.baseURI=readconf1.getDataServiceURL();
		 * 
		 * APIResources resourceAPI=APIResources.valueOf(resource);
		 * 
		 * 
		 * Response
		 * response1=given().log().all().header("Content-Type","application/json")
		 * .body(new String(Files.readAllBytes(Paths.get(System.getProperty("user.dir")+
		 * "/JSONFiles/add_by_path.json")))) .when().post(resourceAPI.getResource())
		 * .then().extract().response();
		 * 
		 * String string_resp=response1.asString(); System.out.println(string_resp);
		 * 
		 * JsonPath js=new JsonPath(string_resp);
		 * 
		 * datasetid=js.getString("data.dataset_id");
		 * versionid=js.getString("data.version_id"); //System.out.println(datasetid);
		 * //System.out.println(versionid);
		 * 
		 * 
		 * }
		 */
	
	
	@Then("column names in API response data should be equal to show column given in input file")
	public void column_names_in_api_response_data_should_be_equal_to_show_column_given_in_input_file() 
	{
		
		
		if(mapObject1.containsKey("show_cols"))
		  {
			
			 filename=(String) mapObject1.get("file_name");	
			
			try {
			
				myList =(List<String>) mapObject1.get("show_cols");
			}
			
			catch(NullPointerException e) {
				System.out.println("NullPointerException thrown!");
			}
			
			
		  
		  Map<String, Integer> datamap = new HashMap<String, Integer>();
		  
		  try {
		  datamap=(js.get("data[\""+filename+"\"].data[0]")); 
		  }
		  
		  catch(NullPointerException e) {
				System.out.println("NullPointerException thrown!");
			}
		  
		  List<String> datacolumnname = new ArrayList<String>(); 
		  for ( String key : datamap.keySet() ) 
		  { 
			  datacolumnname.add(key); 
		  } //System.out.println(datacolumnname);
		  
		  boolean boolval= datacolumnname.containsAll(myList);
		  assertTrue(boolval);
		  }
		
		else System.out.println("show_cols not present!!!");
		
	}
	
	
	@Then("column name is sorted as per sort_obj in request")
	public void column_name_is_sorted_as_per_sort_obj_in_request() {
	    // Write code here that turns the phrase above into concrete actions
		if(mapObject1.containsKey("sort_obj")) 
			  
		  {
	  
	 

				boolean bool;
	  
				Check_sorted_columns Check_obj=new Check_sorted_columns(); 
				bool=Check_obj.check_sorted(js,mapObject1); 
				assertTrue(bool);
	  
		  }
		  else System.out.println("sort_obj not present");
	}
	
	
	
	@Then("check operation of autofill missing value")
	public void check_operation_of_autofill_missing_value() 
	{
	    
		
		  
		  
	if(mapObject1.containsKey("operations"))
{
		  
		 
		  
		  int datasize=js.getInt("data[\""+filename+"\"].data.size()");
		  
		  try
		  {
		  
		  operationobj = (List<Map<String, Object>>) mapObject1.get("operations");
		  }
		  
		  catch(NullPointerException e) {
				System.out.println("NullPointerException thrown!");
			}
			int size=operationobj.size();
			for(int i=0;i<size;i++)
	{
				operation_col_name=(List<String>) operationobj.get(i).get("col_names");
				
				
				//System.out.println(operation_col_name);
				
	try {
				
			for(int j=0;j<operation_col_name.size();j++)
			{
				for(int k=0;k<datasize;k++)
				{
					String columnname=operation_col_name.get(j);
					Object strAr1=js.get("data[\""+filename+"\"].data["+k+"][\""+columnname+"\"]");
					assertTrue(strAr1!=null);
					
				}
			}
			
		}
				
				catch(NullPointerException e) {
					System.out.println("NullPointerException thrown!");
				}
				
	}
		  
}
	else System.out.println("operations not present"); 
		
		

	}
	@Then("number of rows given in row_count in request is Equal to no of rows in Response data")
	public void number_of_rows_given_in_row_count_in_request_is_equal_to_no_of_rows_in_response_data() 
	{
	    // Write code here that turns the phrase above into concrete actions
		if(mapObject1.containsKey("row_count"))
		  {
		  
			
			try
			{
			filename=(String) mapObject1.get("file_name");
			rowcount= (int) mapObject1.get("row_count");
			
			}
			
			
			catch(NullPointerException e) {
				System.out.println("NullPointerException thrown!");
			}
			
			
	  int  count=js.getInt("data[\""+filename+"\"].data.size()");
	  assertEquals(count,rowcount); 
		  }
		  
		  else System.out.println("row_count not present");
	}
	

}
