package corp.kora.global.response;

import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@ToString
public final class ApiResponse<T> {

    private Boolean success;
    private int code;
    private HttpStatus status;
    private String message;
    private T data;

    private ApiResponse(HttpStatus status, String message, T data) {
        this.success = isSuccess(status.value());
        this.code = status.value();
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public static <T> ApiResponse<T> from(HttpStatus httpStatus, String message, T data) {
        return new ApiResponse<>(httpStatus, message, data);
    }

    public static <T> ApiResponse<T> from(HttpStatus httpStatus, T data) {
        return from(httpStatus, httpStatus.name(), data);
    }

    public static <T> ApiResponse<T> from(HttpStatus httpStatus, String message) {
        return from(httpStatus, message, null);
    }


    public static <T> ApiResponse<T> badRequest(String message) {
        return from(HttpStatus.BAD_REQUEST, message);
    }

    public static <T> ApiResponse<T> notFound(String message) {
        return from(HttpStatus.NOT_FOUND, message);
    }

    public static <T> ApiResponse<T> forbidden(String message) {
        return from(HttpStatus.FORBIDDEN, message);
    }

    public static <T> ApiResponse<T> unauthorized(String message) {
        return from(HttpStatus.UNAUTHORIZED, message);
    }

    private Boolean isSuccess(int code) {
        if (code >= 200 && code < 300) {
            return true;
        }
        if (code >= 400) {
            return false;
        }
        return null;
    }

}
