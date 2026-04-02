package com.tradenest.api.dto;

import jakarta.validation.constraints.*;

public class RegisterRequest {

    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 60, message = "Name must be 2–60 characters")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email address")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    // Indian mobile: starts with 6–9, exactly 10 digits
    @Pattern(
        regexp  = "^[6-9]\\d{9}$",
        message = "Phone must be a valid 10-digit Indian mobile number"
    )
    private String phone;

    @NotBlank(message = "City is required")
    private String city;

    private String state;

    // ─── Getters / Setters ────────────────────────────────────────────────────

    public String getName()     { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail()    { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getPhone()    { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getCity()     { return city; }
    public void setCity(String city) { this.city = city; }

    public String getState()    { return state; }
    public void setState(String state) { this.state = state; }
}