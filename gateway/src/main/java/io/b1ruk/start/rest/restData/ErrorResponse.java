package io.b1ruk.start.rest.restData;

import java.time.LocalDateTime;

public record ErrorResponse(String message, int statusCode, LocalDateTime timestamp) {
}
