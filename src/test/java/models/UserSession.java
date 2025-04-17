package models;

public record UserSession (
        String userId,
        String username,
        String password,
        String token,
        String expires,
        String created_date,
        String isActive
) {
}
