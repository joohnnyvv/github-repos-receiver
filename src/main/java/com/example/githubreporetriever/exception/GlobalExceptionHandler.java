    package com.example.githubreporetriever.exception;

    import com.example.githubreporetriever.model.ApiErrorResponse;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.ControllerAdvice;
    import org.springframework.web.bind.annotation.ExceptionHandler;
    import org.springframework.web.client.HttpClientErrorException;

    @ControllerAdvice
    public class GlobalExceptionHandler {

        @ExceptionHandler(HttpClientErrorException.NotFound.class)
        public ResponseEntity<ApiErrorResponse> handleNotFoundException() {
            ApiErrorResponse errorResponse = new ApiErrorResponse(
                    HttpStatus.NOT_FOUND.value(),
                    "User not found"
            );
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
    }
