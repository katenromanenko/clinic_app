package by.katenromanenko.clinicapp.jwt;

import by.katenromanenko.clinicapp.jwt.dto.JwtResponse;

public interface RefreshTokenService {

    String createRefreshToken(String username);

    JwtResponse refresh(String refreshToken);
}

