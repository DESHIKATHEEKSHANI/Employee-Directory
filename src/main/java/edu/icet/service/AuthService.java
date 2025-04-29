package edu.icet.service;

import edu.icet.dto.AuthRequest;
import edu.icet.dto.AuthResponse;

public interface AuthService {
    AuthResponse login(AuthRequest request);
    AuthResponse register(AuthRequest request);
}
