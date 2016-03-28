package com.mentatmobile.randomquotes;

import java.util.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/")
public class RestServices {

	private static final Logger LOGGER = Logger.getLogger(RestServices.class.getName());
	
	@GET
	@Path("/status")
    @Produces("text/plain")
    public String status(){
		try{
			return "OK";
		}
		finally{
			LOGGER.info("Status returned...");
		}
	};
}
