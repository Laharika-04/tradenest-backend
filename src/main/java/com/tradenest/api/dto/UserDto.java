package com.tradenest.api.dto;

import com.tradenest.api.entity.User;

public class UserDto {

    private Long id;
    private String name;
    private String email;
    private String phone;
    private String city;
    private String state;
    private String avatarUrl;
    private boolean verified;

    public UserDto() {}

    public static UserDto fromEntity(User u) {
        UserDto dto = new UserDto();
        dto.id = u.getId();
        dto.name = u.getName();
        dto.email = u.getEmail();
        dto.phone = u.getPhone();
        dto.city = u.getCity();
        dto.state = u.getState();
        dto.avatarUrl = u.getAvatarUrl();
        dto.verified = u.isVerified();
        return dto;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getCity() { return city; }
    public String getState() { return state; }
    public String getAvatarUrl() { return avatarUrl; }
    public boolean isVerified() { return verified; }
}