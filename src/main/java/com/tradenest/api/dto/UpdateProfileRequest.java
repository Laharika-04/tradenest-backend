package com.tradenest.api.dto;

// PUT /api/auth/profile
public class UpdateProfileRequest {

    private String name;
    private String phone;
    private String city;
    private String state;
    private String avatarUrl;

    // FIX: Password change — frontend was re-logging in to verify old password
    //      which is insecure and broken. Handle it server-side instead.
    private String currentPassword;
    private String newPassword;

    public String getName()            { return name; }
    public void setName(String name)   { this.name = name; }

    public String getPhone()              { return phone; }
    public void setPhone(String phone)    { this.phone = phone; }

    public String getCity()               { return city; }
    public void setCity(String city)      { this.city = city; }

    public String getState()              { return state; }
    public void setState(String state)    { this.state = state; }

    public String getAvatarUrl()                    { return avatarUrl; }
    public void setAvatarUrl(String avatarUrl)      { this.avatarUrl = avatarUrl; }

    public String getCurrentPassword()                        { return currentPassword; }
    public void setCurrentPassword(String currentPassword)    { this.currentPassword = currentPassword; }

    public String getNewPassword()                    { return newPassword; }
    public void setNewPassword(String newPassword)    { this.newPassword = newPassword; }
}