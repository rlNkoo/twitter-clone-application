package pl.rlnkoo.twitter_clone_backend.dto.mapper;

import pl.rlnkoo.twitter_clone_backend.dto.TwitDto;
import pl.rlnkoo.twitter_clone_backend.dto.UserDto;
import pl.rlnkoo.twitter_clone_backend.model.Twit;
import pl.rlnkoo.twitter_clone_backend.model.User;
import pl.rlnkoo.twitter_clone_backend.util.TwitUtil;

import java.util.ArrayList;
import java.util.List;

public class TwitDtoMapper {

    public static TwitDto toTwitDto(Twit twit, User requestUser) {
        UserDto user = UserDtoMapper.toUserDto(twit.getUser());
        boolean isLiked = TwitUtil.isLikedByRequestUser(requestUser, twit);
        boolean isRetwited = TwitUtil.isRetwitedByRequestUser(requestUser, twit);

        List<Long> retwitUserId = new ArrayList<>();
        for (User user1 : twit.getRetwitUsers()) {
            retwitUserId.add(user1.getId());
        }
        TwitDto twitDto = new TwitDto();
        twitDto.setId(twit.getId());
        twitDto.setContent(twit.getContent());
        twitDto.setCreatedAt(twit.getCreatedAt());
        twitDto.setImage(twit.getImage());
        twitDto.setTotalLikes(twit.getLikes().size());
        twitDto.setTotalReplies(twit.getReplyTweets().size());
        twitDto.setTotalRetweets(twit.getRetwitUsers().size());
        twitDto.setUser(user);
        twitDto.setLiked(isLiked);
        twitDto.setRetwit(isRetwited);
        twitDto.setRetwitUsersId(retwitUserId);
        twitDto.setReplyTwits(toTwitDtos(twit.getReplyTweets(), requestUser));
        twitDto.setVideo(twit.getVideo());

        return twitDto;
    }

    public static List<TwitDto> toTwitDtos(List<Twit> twits, User requestUser) {
        List<TwitDto> twitDtos = new ArrayList<>();
        for (Twit twit : twits) {
            TwitDto twitDto = toReplyTwitDto(twit, requestUser);
            twitDtos.add(twitDto);
        }
        return twitDtos;
    }

    private static TwitDto toReplyTwitDto(Twit twit, User requestUser) {
        UserDto user = UserDtoMapper.toUserDto(twit.getUser());
        boolean isLiked = TwitUtil.isLikedByRequestUser(requestUser, twit);
        boolean isRetwited = TwitUtil.isRetwitedByRequestUser(requestUser, twit);

        List<Long> retwitUserId = new ArrayList<>();
        for (User user1 : twit.getRetwitUsers()) {
            retwitUserId.add(user1.getId());
        }
        TwitDto twitDto = new TwitDto();
        twitDto.setId(twit.getId());
        twitDto.setContent(twit.getContent());
        twitDto.setCreatedAt(twit.getCreatedAt());
        twitDto.setImage(twit.getImage());
        twitDto.setTotalLikes(twit.getLikes().size());
        twitDto.setTotalReplies(twit.getReplyTweets().size());
        twitDto.setTotalRetweets(twit.getRetwitUsers().size());
        twitDto.setUser(user);
        twitDto.setLiked(isLiked);
        twitDto.setRetwit(isRetwited);
        twitDto.setRetwitUsersId(retwitUserId);
        twitDto.setVideo(twit.getVideo());

        return twitDto;
    }
}
