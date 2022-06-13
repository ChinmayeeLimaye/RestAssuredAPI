Feature: Validation of Get_Rows API


Scenario Outline: Verify Get_Rows passes positive test cases
Given the user has datasetid and versionid through "AddByPathAPI"
When the user calls "GetRowsAPI" using request payload "<JSONFile>"
Then user should get status code 200
And user should get status line as "HTTP/1.0 200 OK"
And response body should not be null
And response time should be less than 15000
And message in result should be "success"
And column names in API response data should be equal to show column given in input file
And column name is sorted as per sort_obj in request
And check operation of autofill missing value
And number of rows given in row_count in request is Equal to no of rows in Response data



Examples:
	|JSONFile|
	|get_rows.json|
	


Scenario: Verify Get_Rows when no input file is given
Given the user has datasetid and versionid through "AddByPathAPI"
When the user calls "GetRowsAPI" using request payload "get_rows_fail.json"
Then user should get status code 500
And user should get status line as "HTTP/1.0 500 INTERNAL SERVER ERROR"
And message in result should be "fail"


Scenario: Verify Get_Rows when no datasetid or version id is provided
Given the user provides no  datasetid and versionid
When the user calls "GetRowsAPI" using request payload "get_rows_fail.json"
Then user should get status code 400
And user should get status line as "HTTP/1.0 400 BAD REQUEST"
And message in result should be "fail"
