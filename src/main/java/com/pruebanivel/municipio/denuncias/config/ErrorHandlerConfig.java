package com.pruebanivel.municipio.denuncias.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.pruebanivel.municipio.denuncias.exception.GeneralException;
import com.pruebanivel.municipio.denuncias.exception.NoDataFoundException;
import com.pruebanivel.municipio.denuncias.exception.ValidateException;
import com.pruebanivel.municipio.denuncias.util.WrapperResponse;

@ControllerAdvice
public class ErrorHandlerConfig extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> all(Exception e, WebRequest request){
		WrapperResponse<?> response = new WrapperResponse<>(false, "Internal server error", null);
		return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(ValidateException.class)
	public ResponseEntity<?> validation(ValidateException e, WebRequest request){
		WrapperResponse<?> response = new WrapperResponse<>(false, e.getMessage(), null);
		return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(NoDataFoundException.class)
	public ResponseEntity<?> noData(NoDataFoundException e, WebRequest request){
		WrapperResponse<?> response = new WrapperResponse<>(false, e.getMessage(), null);
		return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(GeneralException.class)
	public ResponseEntity<?> general(GeneralException e, WebRequest request){
		WrapperResponse<?> response = new WrapperResponse<>(false, "Internal server error", null);
		return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
