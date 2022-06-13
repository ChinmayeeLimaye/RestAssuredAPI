Feature: Validation of Get_col_stats API


Scenario Outline: Verify Get_col_stats passes positive test cases
Given the user has datasetid and versionid through "AddByPathAPI"
When the user calls "GetColStatsAPI" using request payload "<JSONFile>"
Then user should get status code 200
And user should get status line as "HTTP/1.0 200 OK"
And response body should not be null
And response time should be less than 15000
And message in result should be "success"
And column names in API response data should be equal to column names given in input file



Examples:
	|JSONFile|
	|get_col_stats.json|
	|get_col_stats_homes.json|


Scenario: Verify Get_col_stats when no input file is given
Given the user has datasetid and versionid through "AddByPathAPI"
When the user calls "GetColStatsAPI" using request payload "get_col_stats_fail.json"
Then user should get status code 400
And user should get status line as "HTTP/1.0 400 BAD REQUEST"
And message in result should be "fail"


Scenario: Verify Get_col_stats when no datasetid or version id is provided
Given the user provides no  datasetid and versionid
When the user calls "GetColStatsAPI" using request payload "get_col_stats_fail.json"
Then user should get status code 400
And user should get status line as "HTTP/1.0 400 BAD REQUEST"
And message in result should be "fail"
