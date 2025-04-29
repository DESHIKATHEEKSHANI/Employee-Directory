package edu.icet.service.impl;

import edu.icet.dto.AuthRequest;
import edu.icet.dto.AuthResponse;
import edu.icet.entity.User;
import edu.icet.repository.UserRepository;
import edu.icet.security.JwtService;
import edu.icet.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;  // Make this field final
    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Override
    public AuthResponse login(AuthRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );

            User user = userRepository.findByUsername(request.getUsername())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            String token = jwtService.generateToken(user.getUsername(), user.getRole());

            return new AuthResponse(token);
        } catch (AuthenticationException e) {
            throw new RuntimeException("Invalid username or password");
        }
    }

    @Override
    public AuthResponse register(AuthRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        User newUser = new User();
        newUser.setUsername(request.getUsername());
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));  // This line should work now
        newUser.setRole("HR");

        userRepository.save(newUser);

        String token = jwtService.generateToken(newUser.getUsername(), newUser.getRole());
        return new AuthResponse(token);
    }
}

