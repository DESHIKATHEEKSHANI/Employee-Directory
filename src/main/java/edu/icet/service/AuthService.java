package edu.icet.service;

import edu.icet.dto.AuthRequest;
import edu.icet.dto.AuthResponse;
import edu.icet.dto.RegisterRequest;

public interface AuthService {
    AuthResponse login(AuthRequest request);
    AuthResponse register(RegisterRequest request);
}
