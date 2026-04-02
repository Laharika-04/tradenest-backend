package com.tradenest.api.service;

import com.tradenest.api.dto.*;
import com.tradenest.api.entity.User;
import com.tradenest.api.repository.UserRepository;
import com.tradenest.api.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder encoder,
                       JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.encoder        = encoder;
        this.jwtUtil        = jwtUtil;
    }

    public AuthResponse register(RegisterRequest req) {
        if (userRepository.existsByEmailIgnoreCase(req.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        User u = new User();
        u.setName(req.getName().trim());
        u.setEmail(req.getEmail().toLowerCase().trim());
        u.setPassword(encoder.encode(req.getPassword()));

        // Normalise phone: strip non-digits (safety net — frontend already does this)
        String phone = req.getPhone() == null ? null
                       : req.getPhone().replaceAll("\\D", "");
        u.setPhone(phone);
        u.setCity(req.getCity());
        u.setState(req.getState());
        u.setVerified(true);

        User saved = userRepository.save(u);
        return new AuthResponse(jwtUtil.generateToken(saved.getId()),
                                UserDto.fromEntity(saved));
    }

    public AuthResponse login(LoginRequest req) {
        User user = userRepository
                .findByEmail(req.getEmail().toLowerCase().trim())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!encoder.matches(req.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        return new AuthResponse(jwtUtil.generateToken(user.getId()),
                                UserDto.fromEntity(user));
    }

    public UserDto getProfile(Long userId) {
        return UserDto.fromEntity(
                userRepository.findById(userId)
                        .orElseThrow(() -> new RuntimeException("User not found"))
        );
    }

    // ── Profile update (name / phone / city / state / avatar) ─────────────────
    @Transactional
    public UserDto updateProfile(Long userId, UpdateProfileRequest req) {
        User u = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (req.getName()      != null && !req.getName().isBlank())
            u.setName(req.getName().trim());
        if (req.getPhone()     != null && !req.getPhone().isBlank())
            u.setPhone(req.getPhone().replaceAll("\\D", ""));
        if (req.getCity()      != null && !req.getCity().isBlank())
            u.setCity(req.getCity());
        if (req.getState()     != null)
            u.setState(req.getState());
        if (req.getAvatarUrl() != null)
            u.setAvatarUrl(req.getAvatarUrl());

        // FIX: Password change — verify current password then set new one
        if (req.getNewPassword() != null && !req.getNewPassword().isBlank()) {
            if (req.getCurrentPassword() == null || req.getCurrentPassword().isBlank()) {
                throw new RuntimeException("Current password is required to change password");
            }
            if (!encoder.matches(req.getCurrentPassword(), u.getPassword())) {
                throw new RuntimeException("Current password is incorrect");
            }
            if (req.getNewPassword().length() < 6) {
                throw new RuntimeException("New password must be at least 6 characters");
            }
            u.setPassword(encoder.encode(req.getNewPassword()));
        }

        return UserDto.fromEntity(userRepository.save(u));
    }
}