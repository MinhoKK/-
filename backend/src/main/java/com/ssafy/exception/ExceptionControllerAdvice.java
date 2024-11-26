package com.ssafy.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ssafy.exception.custom.UnAuthorizedException;

import io.jsonwebtoken.JwtException;

@RestControllerAdvice
public class ExceptionControllerAdvice {
	
//	@ExceptionHandler(Exception.class)
//    public ResponseEntity<ErrorResponse> handleRuntimeException(final RuntimeException e) {
//        return ResponseEntity.badRequest()
//                .body(new ErrorResponse(e.getMessage()));
//    }
	
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(final RuntimeException e) {
        return ResponseEntity.internalServerError()
                .body(new ErrorResponse(e.getMessage()));
    }
	
	@ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(final RuntimeException e) {
        return ResponseEntity.badRequest()
                .body(new ErrorResponse(e.getMessage()));
    }
	
	@ExceptionHandler(JwtException.class)
	public ResponseEntity<ErrorResponse> handleJwtException(final RuntimeException e){
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
				.body(new ErrorResponse(e.getMessage()));
	}
	
	@ExceptionHandler(UnAuthorizedException.class)
	public ResponseEntity<ErrorResponse> handleUnAuthorizedException(final RuntimeException e){
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
				.body(new ErrorResponse(e.getMessage()));
	}
	
	@ExceptionHandler(NullPointerException.class)
	public ResponseEntity<ErrorResponse> handleNullPointerException(final RuntimeException e){
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(new ErrorResponse(e.getMessage()));
	}
	
	@ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateKeyException(final RuntimeException e) {
        return ResponseEntity.badRequest()
                .body(new ErrorResponse(e.getMessage()));
    }
}
