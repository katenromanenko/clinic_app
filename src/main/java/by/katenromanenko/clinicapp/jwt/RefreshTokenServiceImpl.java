package by.katenromanenko.clinicapp.jwt;

import by.katenromanenko.clinicapp.jwt.dto.JwtResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final StringRedisTemplate redisTemplate;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;

    @Value("${jwt.refresh-token.lifetime}")
    private Duration refreshLifetime;

    private String key(String refreshToken) {
        return "refresh:" + refreshToken;
    }

    @Override
    public String createRefreshToken(String username) {

        String refreshToken = UUID.randomUUID().toString();

        redisTemplate.opsForValue().set(key(refreshToken), username, refreshLifetime);

        return refreshToken;
    }

    @Override
    public JwtResponse refresh(String refreshToken) {

        String username = redisTemplate.opsForValue().get(key(refreshToken));
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("Refresh token не валиден или истек");
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        if (!userDetails.isEnabled()) {
            throw new IllegalArgumentException("Пользователь не активен");
        }

        redisTemplate.delete(key(refreshToken));

        String newRefreshToken = createRefreshToken(username);
        String newAccessToken = jwtService.generateToken(userDetails);

        return new JwtResponse(newAccessToken, newRefreshToken);
    }
}
