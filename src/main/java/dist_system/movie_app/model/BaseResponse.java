package dist_system.movie_app.model;

import lombok.Data;

@Data
public class BaseResponse<T> {
    private final Boolean status;
    private final String message;
    private final T payload;

    public BaseResponse(Boolean status, String message, T payload) {
        this.status = status;
        this.message = message;
        this.payload = payload;
    }

    public String getMessage() {
        return message;
    }
}
