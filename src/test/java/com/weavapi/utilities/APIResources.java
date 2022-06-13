package com.weavapi.utilities;

public enum APIResources {
	
	AddByPathAPI("/api/v1/dataset/add_by_path"),
	GetColStatsAPI("/api/v1/get_col_stats"),
	GetRowsAPI("/api/v1/get_rows"),
	ConfigureConnectorAPI("/api/v1/connector/configure"),
	ViewConnectorAPI("/api/v1/connector/get"),
	UpdateConnectorAPI("/api/v1/connector/update"),
	DeleteConnectorAPI("/api/v1/connector/delete"),
	CreateWorkspaceAPI("/api/v1/workspace/create");
	
	
	
private String resource;
	
	APIResources(String resource)
	{
		this.resource=resource;
	}
	
	public String getResource()
	{
		return resource;
	}

}
