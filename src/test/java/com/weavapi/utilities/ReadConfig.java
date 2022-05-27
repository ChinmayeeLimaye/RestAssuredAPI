package com.weavapi.utilities;
import java.io.FileInputStream;

import java.util.Properties;



public class ReadConfig {



	

		Properties prop;

		

		public ReadConfig()

		{

			try

			{

			prop=new Properties();

			FileInputStream fis=new FileInputStream(System.getProperty("user.dir")+"/src/test/java/com/weavapi/utilities/config.properties");

			prop.load(fis);

			}

		

			catch(Exception e)

			{

				System.out.println("Error message"+e.getMessage());

			}



	



}

		

		public String getDataServiceURL() {

			String Url=prop.getProperty("Data_service_url");

			return Url;

		}
		
		
		public String getDataBrowserURL() {

			String Url=prop.getProperty("Data_browser_url");

			return Url;

		}


		public String getMarketplaceURL() {

			String Url=prop.getProperty("Marketplace_service_url");

			return Url;

		}
		
		
		public String getDataFlowURL() {

			String Url=prop.getProperty("Dataflow_service_url");

			return Url;

		}
		
		
		
		
		public String getIngestionURL() {

			String Url=prop.getProperty("Ingestion_service_url");

			return Url;

		}

		

		

		}