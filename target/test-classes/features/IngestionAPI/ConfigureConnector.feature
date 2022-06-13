Feature: Validation of Configure_Connector and Update_Connector API


Scenario Outline: Verify Configure_Connector passes positive test cases
Given the user has workspace_id through "CreateWorkspaceAPI"
When the user calls "ConfigureConnectorAPI" using request payload "<JSONFile>"
Then Then user should get status code 200
And user should get status line as "HTTP/1.0 200 OK"
And response body should not be null
And response time should be less than 15000
And message in result should be "success"
And verify connector_id created maps to connector_name using "ViewConnectorAPI"


Examples:
	|JSONFile|
	|configure_connector.json|
	
	
	
Scenario Outline: Verify Update_Connector passes positive test cases
Given the user has connector_id through "ConfigureConnectorAPI"
When the user calls "UpdateConnectorAPI" using request payload "<JSONFile>"
Then Then user should get status code 200
And user should get status line as "HTTP/1.0 200 OK"
And response body should not be null
And response time should be less than 15000
And message in result should be "success"




Examples:
	|JSONFile|
	|update_connector.json|	
	
	