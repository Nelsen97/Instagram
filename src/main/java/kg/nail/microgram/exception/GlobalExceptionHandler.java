package kg.nail.microgram.exception;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import kg.nail.microgram.dto.exception.ErrorExceptionResponse;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Objects;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = BadRequestException.class)
    public ResponseEntity<ErrorExceptionResponse> handleBadRequestException(BadRequestException ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorExceptionResponse.builder()
                        .message(ex.getMessage())
                        .timestamp(System.currentTimeMillis())
                        .build());

    }

    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<ErrorExceptionResponse> handleNotFoundException(NotFoundException ex) {
        log.error(ex.getMessage(), ex);

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ErrorExceptionResponse.builder()
                        .message(ex.getMessage())
                        .timestamp(System.currentTimeMillis())
                        .build());

    }

    @ExceptionHandler(value = FileStorageException.class)
    public ResponseEntity<ErrorExceptionResponse> handleFileStorageException(FileStorageException ex) {
        log.error(ex.getMessage(), ex);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorExceptionResponse.builder()
                        .message(ex.getMessage())
                        .timestamp(System.currentTimeMillis())
                        .build());

    }

    @ExceptionHandler(value = BadCredentialsException.class)
    public ResponseEntity<ErrorExceptionResponse> handleBadCredentialsException(BadCredentialsException ex) {
        log.error(ex.getMessage(), ex);

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ErrorExceptionResponse.builder()
                        .message("Неверный логин или пароль")
                        .timestamp(System.currentTimeMillis())
                        .build());
    }

    @ExceptionHandler(value = DisabledException.class)
    public ResponseEntity<ErrorExceptionResponse> handleDisabledException(DisabledException ex) {
        log.error(ex.getMessage(), ex);

        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(ErrorExceptionResponse.builder()
                        .message("Пользователь заблокирован")
                        .timestamp(System.currentTimeMillis())
                        .build());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(@NonNull MethodArgumentNotValidException ex,
                                                                  @NonNull HttpHeaders headers,
                                                                  @NonNull HttpStatusCode status,
                                                                  @NonNull WebRequest request) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                        ErrorExceptionResponse.builder()
                                .message("Неверное тело запроса")
                                .errors(ex.getBindingResult().getFieldErrors().stream()
                                        .map(error -> ErrorExceptionResponse.FieldErrorDetail.builder()
                                                .field(error.getField())
                                                .message(error.getDefaultMessage())
                                                .build())
                                        .toList())
                                .timestamp(System.currentTimeMillis())
                                .build()
                );
    }

    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorExceptionResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorExceptionResponse.builder()
                        .message("Ошибка в параметре запроса: '%s' не может быть преобразован в тип %s"
                                .formatted(ex.getName(), Objects.requireNonNull(ex.getRequiredType()).getSimpleName()))
                        .timestamp(System.currentTimeMillis())
                        .build());
    }

    @ExceptionHandler
    public ResponseEntity<ErrorExceptionResponse> handleAccessDeniedException(AccessDeniedException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(ErrorExceptionResponse.builder()
                        .message("Доступ запрещен")
                        .timestamp(System.currentTimeMillis())
                        .build());
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
            @NonNull HttpRequestMethodNotSupportedException ex,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatusCode status,
            @NonNull WebRequest request) {

        String supportedMethods = ex.getSupportedMethods() != null
                ? String.join(", ", ex.getSupportedMethods())
                : "неизвестно";

        return ResponseEntity.status(status)
                .body(ErrorExceptionResponse.builder()
                        .message("Метод: %s не поддерживается. Разрешенные методы: %s".formatted(ex.getMethod(), supportedMethods))
                        .timestamp(System.currentTimeMillis())
                        .build());
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(@NonNull NoHandlerFoundException ex,
                                                                   @NonNull HttpHeaders headers,
                                                                   @NonNull HttpStatusCode status,
                                                                   @NonNull WebRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ErrorExceptionResponse.builder()
                        .message("Маршрут %s %s не найден".formatted(ex.getHttpMethod(), ex.getRequestURL()))
                        .timestamp(System.currentTimeMillis())
                        .build());
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(@NonNull HttpMessageNotReadableException ex,
                                                                  @NonNull HttpHeaders headers,
                                                                  @NonNull HttpStatusCode status,
                                                                  @NonNull WebRequest request) {
        String errorMessage = "Некорректный JSON: невозможно обработать запрос";

        if (ex.getCause() instanceof MismatchedInputException mismatchedEx) {
            String fieldName = mismatchedEx.getPath().stream()
                    .map(JsonMappingException.Reference::getFieldName)
                    .filter(Objects::nonNull)
                    .collect(Collectors.joining("."));

            if (!fieldName.isEmpty()) {
                errorMessage = "Ошибка в поле: " + fieldName + ". Проверьте правильность передаваемых данных";
            }
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorExceptionResponse.builder()
                        .message(errorMessage)
                        .timestamp(System.currentTimeMillis())
                        .build());
    }

    @Override
    protected ResponseEntity<Object> handleMaxUploadSizeExceededException(@NonNull MaxUploadSizeExceededException ex,
                                                                          @NonNull HttpHeaders headers,
                                                                          @NonNull HttpStatusCode status,
                                                                          @NonNull WebRequest request) {
        return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE)
                .body(ErrorExceptionResponse.builder()
                        .message("Размер загружаемого файла превысил допустимый лимит")
                        .timestamp(System.currentTimeMillis())
                        .build());
    }


    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(@NonNull HttpMediaTypeNotSupportedException ex,
                                                                     @NonNull HttpHeaders headers,
                                                                     @NonNull HttpStatusCode status,
                                                                     @NonNull WebRequest request) {

        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                .body(ErrorExceptionResponse.builder()
                        .message("Неподдерживаемый тип медиа: " + ex.getContentType())
                        .timestamp(System.currentTimeMillis())
                        .build());
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorExceptionResponse> handleException(Exception ex) {
        long timestamp = System.currentTimeMillis();
        log.error(ex.getMessage(), timestamp, ex);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorExceptionResponse.builder()
                        .message("Ошибка сервера")
                        .timestamp(timestamp)
                        .build());
    }
}
