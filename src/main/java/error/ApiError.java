package error;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
public class ApiError {
    private LocalDateTime timestamp;
    private String errorMassage;
    private HttpStatus statusCode;

    public ApiError(){
        this.timestamp = LocalDateTime.now();
    }

    public ApiError(String errorMassage, HttpStatus statusCode) {
        this.timestamp = LocalDateTime.now();
        this.errorMassage = errorMassage;
        this.statusCode = statusCode;
    }
}
