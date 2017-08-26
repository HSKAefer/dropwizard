package de.dokukaefer.btp.exceptions;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class UnprocessableExceptionMapper implements ExceptionMapper<UnprocessableException>{

	@Override
	public Response toResponse(UnprocessableException exception) {
		Response response500 = Response.status(exception.getCode()).
				entity(exception.getMessage()).
				type(MediaType.APPLICATION_JSON).build();
		
		return response500;
	}

}
