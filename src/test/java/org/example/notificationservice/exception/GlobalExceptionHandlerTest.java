package org.example.notificationservice.exception;

import org.example.notificationservice.controller.dto.ErrorResponse;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler exceptionHandler = new GlobalExceptionHandler();

    @Test
    void handleEmailSendingException_ShouldReturnInternalServerError() {

        EmailSendingException exception = new EmailSendingException("Test error");


        ResponseEntity<ErrorResponse> response = exceptionHandler.handleEmailSendingException(exception);


        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("EMAIL_SENDING_ERROR", response.getBody().getError());
        assertEquals("Test error", response.getBody().getMessage());
    }

    @Test
    void handleValidationExceptions_ShouldReturnBadRequest() {

        MethodArgumentNotValidException exception = mock(MethodArgumentNotValidException.class);
        BindingResult bindingResult = mock(BindingResult.class);
        FieldError fieldError = new FieldError("object", "field", "default message");

        when(exception.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getFieldErrors()).thenReturn(List.of(fieldError));


        ResponseEntity<ErrorResponse> response = exceptionHandler.handleValidationExceptions(exception);


        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("VALIDATION_ERROR", response.getBody().getError());
        assertEquals("default message", response.getBody().getMessage());
    }

    @Test
    void handleGenericException_ShouldReturnInternalServerError() {

        Exception exception = new RuntimeException("Generic error");

        ResponseEntity<ErrorResponse> response = exceptionHandler.handleGenericException(exception);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("INTERNAL_SERVER_ERROR", response.getBody().getError());
        assertEquals("Произошла внутренняя ошибка сервера", response.getBody().getMessage());
    }
}