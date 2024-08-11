package pl.rlnkoo.twitter_clone_backend.util;

import pl.rlnkoo.twitter_clone_backend.model.Like;
import pl.rlnkoo.twitter_clone_backend.model.Twit;
import pl.rlnkoo.twitter_clone_backend.model.User;

public class TwitUtil {

    public final static boolean isLikedByRequestUser(User requestUser, Twit twit) {
        for (Like like : twit.getLikes()) {
            if (like.getUser().getId().equals(requestUser.getId())) {
                return true;
            }
        }
        return false;
    }

    public final static boolean isRetwitedByRequestUser(User requestUser, Twit twit) {
        for (User user : twit.getRetwitUsers()) {
            if (user.getId().equals(requestUser.getId())) {
                return true;
            }
        }
        return false;
    }
}
