package com.example.parsing.errors;

import com.example.parsing.utils.ApiUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.List;

import static com.example.parsing.utils.ApiUtils.error;

@ControllerAdvice
public class GeneralExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private ResponseEntity<ApiUtils.ApiResult<?>> newResponse(Throwable throwable, HttpStatus status) {
        return newResponse(throwable.getMessage(), status);
    }

    private ResponseEntity<ApiUtils.ApiResult<?>> newResponse(String message, HttpStatus status) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<>(error(message, status), headers, status);
    }

    @ExceptionHandler({
            NoHandlerFoundException.class,
    })
    public ResponseEntity<?> handleNotFoundException(Exception e) {
        return newResponse(e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({
            IllegalArgumentException.class,
            IllegalStateException.class,
            MethodArgumentNotValidException.class,
            ResourceAccessException.class,
            BindException.class
    })
    public ResponseEntity<?> handleBadRequestException(Exception e) {
        log.info("Bad request exception occurred: {}", e.getMessage());
        if (e instanceof MethodArgumentNotValidException) { // requestBody
            return newResponse(
                    ((MethodArgumentNotValidException)e).getBindingResult().getAllErrors().get(0).getDefaultMessage(),
                    HttpStatus.BAD_REQUEST
            );
        }

        if (e instanceof BindException) { // requestParam, formData
            BindingResult bindingResult = ((BindException)e).getBindingResult();
            StringBuilder sb = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (int i = 0; i < fieldErrors.size(); i++) {
                FieldError fieldError = fieldErrors.get(i);
                sb.append(fieldError.getDefaultMessage());
                if (i < fieldErrors.size() - 1)
                    sb.append(", ");
            }
            return newResponse(sb.toString(), HttpStatus.BAD_REQUEST);
        }
        return newResponse(e, HttpStatus.BAD_REQUEST);
    }

}
