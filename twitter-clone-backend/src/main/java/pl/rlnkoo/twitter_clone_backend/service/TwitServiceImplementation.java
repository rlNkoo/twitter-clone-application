package pl.rlnkoo.twitter_clone_backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.rlnkoo.twitter_clone_backend.exceptions.TwitException;
import pl.rlnkoo.twitter_clone_backend.exceptions.UserException;
import pl.rlnkoo.twitter_clone_backend.model.Twit;
import pl.rlnkoo.twitter_clone_backend.model.User;
import pl.rlnkoo.twitter_clone_backend.repository.TwitRepository;
import pl.rlnkoo.twitter_clone_backend.request.TwitReplyRequest;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TwitServiceImplementation implements TwitService{

    @Autowired
    private TwitRepository twitRepository;

    @Override
    public Twit createTwit(Twit request, User user) throws UserException {
        Twit twit = new Twit();
        twit.setContent(request.getContent());
        twit.setCreatedAt(LocalDateTime.now());
        twit.setImage(request.getImage());
        twit.setUser(user);
        twit.setReply(false);
        twit.setTwit(true);
        twit.setVideo(request.getVideo());
        return twitRepository.save(twit);
    }

    @Override
    public List<Twit> findAllTwits() {
        return twitRepository.findAllByIsTwitTrueOrderByCreatedAtDesc();
    }

    @Override
    public Twit retwit(Long twitId, User user) throws UserException, TwitException {
        Twit twit = findById(twitId);
        if (twit.getRetwitUsers().contains(user)) {
            twit.getRetwitUsers().remove(user);
        } else {
            twit.getRetwitUsers().add(user);
        }
        return twitRepository.save(twit);
    }

    @Override
    public Twit findById(Long twitId) throws TwitException {
        Twit twit = twitRepository.findById(twitId)
                .orElseThrow(() -> new TwitException("Twit not found with id " + twitId));
        return twit;
    }

    @Override
    public void deleteTwitById(Long twitId, Long userId) throws TwitException, UserException {
        Twit twit = findById(twitId);
        if (!userId.equals(twit.getUser().getId())) {
            throw new UserException("You can't delete another user's twits");
        }
        twitRepository.deleteById(twit.getId());
    }

    @Override
    public Twit removeFromRetwit(Long twitId, User user) throws TwitException, UserException {
        return null;
    }

    @Override
    public Twit createReply(TwitReplyRequest request, User user) throws TwitException {
        Twit replyFor = findById(request.getTwitId());
        Twit twit = new Twit();
        twit.setContent(request.getContent());
        twit.setCreatedAt(LocalDateTime.now());
        twit.setImage(request.getImage());
        twit.setUser(user);
        twit.setReply(true);
        twit.setTwit(false);
        twit.setReplyFor(replyFor);

        Twit savedReply = twitRepository.save(twit);
        replyFor.getReplyTweets().add(savedReply);
        twitRepository.save(replyFor);
        return replyFor;
    }

    @Override
    public List<Twit> getUserTwit(User user) {
        return twitRepository.findByRetwitUsersContainsOrUser_idAndIsTwitTrueOrderByCreatedAtDesc(user, user.getId());
    }

    @Override
    public List<Twit> findByLikesContainsUser(User user) {
        return twitRepository.findByLikesUser_id(user.getId());
    }
}
