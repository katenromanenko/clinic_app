package by.katenromanenko.clinicapp.jwt;

import by.katenromanenko.clinicapp.jwt.dto.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class JwtController {

    private final SignService signService;

    @PostMapping("/sign-in")
    public JwtResponse signIn(@Valid @RequestBody SignInRequest request) {
        return new JwtResponse(signService.signIn(request));
    }
}