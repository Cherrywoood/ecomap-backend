package com.example.ecomapbackend.exception;

import com.example.ecomapbackend.exception.custom.FileException;
import com.example.ecomapbackend.exception.custom.InvalidMediaTypeException;
import com.example.ecomapbackend.exception.custom.NotFoundException;
import jakarta.mail.MessagingException;
import jakarta.persistence.EntityExistsException;
import jakarta.validation.ConstraintViolationException;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.List;

@RestControllerAdvice
public class ResponseExceptionHandler {
    private static final String BAD_REQUEST_MES = "Invalid data";
    private static final String EMAIL_SEND_ERROR = "Failed to send suggestion";
    @Value("${spring.servlet.multipart.max-file-size}")
    private String maxFileSize;

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFoundException(NotFoundException ex, WebRequest request) {
        return new ErrorResponse(
                HttpStatus.NOT_FOUND,
                ex.getMessage(),
                request.getDescription(false)
        );
    }

    @ExceptionHandler(EntityExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleEntityExistsException(EntityExistsException ex, WebRequest request) {
        return new ErrorResponse(
                HttpStatus.CONFLICT,
                ex.getMessage(),
                request.getDescription(false)
        );
    }

    @ExceptionHandler(FileException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleFileException(FileException ex, WebRequest request) {
        return new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                ex.getMessage(),
                request.getDescription(false)
        );
    }

    @ExceptionHandler(FileSizeLimitExceededException.class)
    @ResponseStatus(HttpStatus.PAYLOAD_TOO_LARGE)
    public ErrorResponse handleFileSizeLimitExceededException(FileSizeLimitExceededException ex, WebRequest request) {
        var message = "The file size limit of %s has been exceeded".formatted(maxFileSize);
        return new ErrorResponse(
                HttpStatus.PAYLOAD_TOO_LARGE,
                message,
                request.getDescription(false)
        );
    }

    @ExceptionHandler(InvalidMediaTypeException.class)
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    public ErrorResponse handleNotAllowedException(InvalidMediaTypeException ex, WebRequest request) {
        return new ErrorResponse(
                HttpStatus.UNSUPPORTED_MEDIA_TYPE,
                ex.getMessage(),
                request.getDescription(false)
        );
    }

    @ExceptionHandler(HttpMediaTypeException.class)
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    public ErrorResponse handleHttpMediaTypeException(HttpMediaTypeException ex, WebRequest request) {
        return new ErrorResponse(
                HttpStatus.UNSUPPORTED_MEDIA_TYPE,
                "%s, expected %s".formatted(ex.getMessage(), ex.getSupportedMediaTypes()),
                request.getDescription(false)
        );
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleConstraintViolationException(ConstraintViolationException ex, WebRequest request) {
        List<ErrorResponse.FieldError> fieldErrors = ex.getConstraintViolations()
                .stream()
                .map(fieldError ->
                        new ErrorResponse.FieldError(fieldError.getPropertyPath().toString(), fieldError.getMessage())
                )
                .toList();

        return new ErrorResponse(
                HttpStatus.BAD_REQUEST,
                BAD_REQUEST_MES,
                request.getDescription(false),
                fieldErrors
        );
    }

    @ExceptionHandler(MessagingException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleMessagingException(MessagingException ex, WebRequest request) {
        return new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                EMAIL_SEND_ERROR,
                request.getDescription(false)
        );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleHttpMessageNotReadableException(HttpMessageNotReadableException ex, WebRequest request) {
        return new ErrorResponse(
                HttpStatus.BAD_REQUEST,
                ex.getMessage(),
                request.getDescription(false),
                null
        );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        return new ErrorResponse(
                HttpStatus.BAD_REQUEST,
                ex.getMessage(),
                request.getDescription(false),
                null
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request) {
        List<ErrorResponse.FieldError> fieldErrors = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(fieldError ->
                        new ErrorResponse.FieldError(fieldError.getField(), fieldError.getDefaultMessage())
                )
                .toList();

        return new ErrorResponse(
                HttpStatus.BAD_REQUEST,
                BAD_REQUEST_MES,
                request.getDescription(false),
                fieldErrors
        );
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse handleBadCredentialsException(BadCredentialsException ex, WebRequest request) {
        return new ErrorResponse(
                HttpStatus.UNAUTHORIZED,
                "Неверные учетные данные",
                request.getDescription(false)
        );
    }

}