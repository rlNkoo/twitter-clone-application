package pl.rlnkoo.twitter_clone_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.rlnkoo.twitter_clone_backend.dto.UserDto;
import pl.rlnkoo.twitter_clone_backend.dto.mapper.UserDtoMapper;
import pl.rlnkoo.twitter_clone_backend.exceptions.UserException;
import pl.rlnkoo.twitter_clone_backend.model.User;
import pl.rlnkoo.twitter_clone_backend.service.UserService;
import pl.rlnkoo.twitter_clone_backend.util.UserUtil;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<UserDto> getUserProfile(@RequestHeader("Authorization") String jwt) throws UserException {
        User user = userService.findUserProfileByJwt(jwt);
        UserDto userDto = UserDtoMapper.toUserDto(user);
        userDto.setReg_user(true);
        return new ResponseEntity<UserDto>(userDto, HttpStatus.ACCEPTED);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long userId
            , @RequestHeader("Authorization") String jwt) throws UserException {
        User requestUser = userService.findUserProfileByJwt(jwt);
        User user = userService.findUserById(userId);
        UserDto userDto = UserDtoMapper.toUserDto(user);
        userDto.setReg_user(UserUtil.isReqUser(requestUser, user));
        userDto.setFollowed(UserUtil.isFollowedByReqUser(requestUser, user));
        return new ResponseEntity<UserDto>(userDto, HttpStatus.ACCEPTED);
    }

    @GetMapping("/search")
    public ResponseEntity<List<UserDto>> searchUser(@RequestParam String query
            , @RequestHeader("Authorization") String jwt) throws UserException {
        User requestUser = userService.findUserProfileByJwt(jwt);
        List<User> users = userService.searchUser(query);
        List<UserDto> userDtos = UserDtoMapper.toUserDtos(users);
        return new ResponseEntity<>(userDtos, HttpStatus.ACCEPTED);
    }

    @PutMapping("/update")
    public ResponseEntity<UserDto> updateUser(@RequestBody User request
            , @RequestHeader("Authorization") String jwt) throws UserException {
        User requestUser = userService.findUserProfileByJwt(jwt);
        User user = userService.updateUser(requestUser.getId(), request);
        UserDto userDto = UserDtoMapper.toUserDto(user);
        return new ResponseEntity<>(userDto, HttpStatus.ACCEPTED);
    }

    @PutMapping("/{userId}/follow")
    public ResponseEntity<UserDto> followeUser(@PathVariable Long userId
            , @RequestHeader("Authorization") String jwt) throws UserException {
        User requestUser = userService.findUserProfileByJwt(jwt);
        User user = userService.followUser(userId, requestUser);
        UserDto userDto = UserDtoMapper.toUserDto(user);
        userDto.setFollowed(UserUtil.isFollowedByReqUser(requestUser, user));

        return new ResponseEntity<>(userDto, HttpStatus.ACCEPTED);
    }
}
