package com.mentatmobile.randomquotes;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

@Path("/secure")
public class RestSecureServices {

	private static final Logger LOGGER = Logger.getLogger(RestSecureServices.class.getName());
	
	@GET
	@Path("/image/{id}")
    @Produces("image/png")
    public Response status(@PathParam("id")Integer id){
		String systemDataDir = System.getenv("OPENSHIFT_DATA_DIR");
		String fileName = String.format("image%03d.png", id);
		String filePath = String.format(systemDataDir + "images/image%03d.png", id);
		
		try{
			File file = new File(filePath);
			if(file.exists() && !file.isDirectory()) {
			    BufferedImage image = ImageIO.read(file);
	
			    ByteArrayOutputStream baos = new ByteArrayOutputStream();
			    ImageIO.write(image, "png", baos);
			    byte[] imageData = baos.toByteArray();
			    
			    // uncomment line below to send non-streamed
			    //return Response.ok(imageData).build();
	
			    // uncomment line below to send streamed
				LOGGER.info("Status returned image " + filePath + "...");
			    return Response.ok(new ByteArrayInputStream(imageData))
			            .header("Content-Disposition", "attachment; filename=" + fileName)
			            .build();
			}
		}
		catch(IOException e){
			LOGGER.error(e.getMessage(), e);
		}
		
		LOGGER.info("Count not find file " + filePath + "...");
		return null;
	};
}
