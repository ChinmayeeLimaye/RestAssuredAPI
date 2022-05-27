package com.weavapi.utilities;

public enum APIResources {
	
	AddByPathAPI("/api/v1/dataset/add_by_path"),
	GetColStatsAPI("/api/v1/get_col_stats"),
	GetRowsAPI("/api/v1/get_rows");
	
	
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
