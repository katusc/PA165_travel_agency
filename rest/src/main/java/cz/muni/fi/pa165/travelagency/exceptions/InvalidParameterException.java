/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.travelagency.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception which is used by rest services.
 * @author Katerina Caletkova
 */
@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY, reason = "This resource has invalid parameter")
public class InvalidParameterException extends RuntimeException {
    
    public InvalidParameterException(Throwable cause) {
        super(cause);
    }
  
    
}
