package pl.rlnkoo.twitter_clone_backend.dto;

import lombok.Data;

@Data
public class LikeDto {

    private Long id;

    private UserDto user;

    private TwitDto twit;


}
