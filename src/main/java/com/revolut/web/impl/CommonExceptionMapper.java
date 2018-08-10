package com.revolut.web.impl;

import com.revolut.service.exception.AccountNotFoundException;
import com.revolut.service.exception.CommonAccountException;
import com.revolut.web.dto.ErrorDTO;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Mapping exceptions into responces with valid codes and content.
 */
@Provider
public class CommonExceptionMapper implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception exception) {
        if (exception instanceof CommonAccountException){
            CommonAccountException ourException = (CommonAccountException)exception;
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(ErrorDTO.withMessage(ourException.readableMessage())).build();
        }
        return Response.serverError().entity(ErrorDTO.withMessage(exception.getMessage())).build();
    }
}
