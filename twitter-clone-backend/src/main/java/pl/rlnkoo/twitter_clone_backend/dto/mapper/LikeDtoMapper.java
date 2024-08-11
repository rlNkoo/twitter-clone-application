package pl.rlnkoo.twitter_clone_backend.dto.mapper;

import pl.rlnkoo.twitter_clone_backend.dto.LikeDto;
import pl.rlnkoo.twitter_clone_backend.dto.TwitDto;
import pl.rlnkoo.twitter_clone_backend.dto.UserDto;
import pl.rlnkoo.twitter_clone_backend.model.Like;
import pl.rlnkoo.twitter_clone_backend.model.User;

import java.util.ArrayList;
import java.util.List;

public class LikeDtoMapper {

    public static LikeDto toLikeDto(Like like, User requestUser) {
        UserDto user = UserDtoMapper.toUserDto(like.getUser());
        UserDto requestUserDto = UserDtoMapper.toUserDto(requestUser);
        TwitDto twit = TwitDtoMapper.toTwitDto(like.getTwit(), requestUser);

        LikeDto likeDto = new LikeDto();
        likeDto.setId(like.getId());
        likeDto.setTwit(twit);
        likeDto.setUser(user);
        return likeDto;
    }

    public static List<LikeDto> toLikeDtos(List<Like> likes, User requestUser) {
        List<LikeDto> likeDtos = new ArrayList<>();
        for (Like like : likes) {
            UserDto user = UserDtoMapper.toUserDto(like.getUser());
            TwitDto twit = TwitDtoMapper.toTwitDto(like.getTwit(), requestUser);
            LikeDto likeDto = new LikeDto();
            likeDto.setId(like.getId());
            likeDto.setTwit(twit);
            likeDto.setUser(user);
            likeDtos.add(likeDto);
        }
        return likeDtos;
    }
}
