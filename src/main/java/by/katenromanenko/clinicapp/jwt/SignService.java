package by.katenromanenko.clinicapp.jwt;

import by.katenromanenko.clinicapp.jwt.dto.JwtResponse;
import by.katenromanenko.clinicapp.jwt.dto.SignInRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignService {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;

    public JwtResponse signIn(SignInRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getLogin(),
                        request.getPassword()
                )
        );

        UserDetails userDetails =
                userDetailsService.loadUserByUsername(request.getLogin());

        if (!userDetails.isEnabled()) {
            throw new IllegalArgumentException("Пользователь не активен");
        }

        String accessToken = jwtService.generateToken(userDetails);
        String refreshToken = refreshTokenService.createRefreshToken(userDetails.getUsername());

        return new JwtResponse(accessToken, refreshToken);
    }
}