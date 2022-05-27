package com.weavapi.utilities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import io.restassured.response.Response;

public class Validation_method {
	
	
	
	public void  checkResponseCode(Response res,int statuscode)
	{
		
		assertEquals(res.getStatusCode(),statuscode);
		
	}
	
	
	public void  checkStatusLine(Response res,String statusline)
	{
		
		assertEquals(res.getStatusLine(),statusline);
		
	}
	
	
	
	public void  checkResponseBody(Response res)
	{
		
		assertNotNull(res.getBody().asString());
		
	}
	
	
	public void  checkResponseTime(Response res,int responsetime)
	{
		
		assertTrue(res.getTime()<responsetime);
		
	}

}
