package dist_system.movie_app.model;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Builder
public class BaseResponse<T> {
    private final Boolean status;
    @Getter
    private final String message;
    private final T payload;

    public BaseResponse(Boolean status, String message, T payload) {
        this.status = status;
        this.message = message;
        this.payload = payload;
    }
}
