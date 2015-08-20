package com.swainbrooks.doperlite.exeptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by swain on 8/19/15.
 */
public class CustomBadRequestResponse extends WebApplicationException {

    public CustomBadRequestResponse() {
        super(Response.status(Response.Status.BAD_REQUEST).build());
    }

    public CustomBadRequestResponse(String message) {
        super(Response.status(Response.Status.BAD_REQUEST).entity(message).type(MediaType.TEXT_PLAIN).build());
    }
}
