package pl.rlnkoo.twitter_clone_backend.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserDto {

    private Long id;

    private String fullName;

    private String email;

    private String image;

    private String location;

    private String website;

    private String birthDate;

    private String mobile;

    private String backgroundImage;

    private String bio;

    private boolean reg_user;

    private boolean login_with_google;

    private List<UserDto> followers = new ArrayList<>();

    private List<UserDto> followings = new ArrayList<>();

    private boolean followed;

    private boolean isVerified;
}
