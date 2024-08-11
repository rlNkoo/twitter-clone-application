package pl.rlnkoo.twitter_clone_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.rlnkoo.twitter_clone_backend.model.Twit;
import pl.rlnkoo.twitter_clone_backend.model.User;

import java.util.List;

public interface TwitRepository extends JpaRepository<Twit, Long> {

    List<Twit> findAllByIsTwitTrueOrderByCreatedAtDesc();

    List<Twit> findByRetwitUsersContainsOrUser_idAndIsTwitTrueOrderByCreatedAtDesc(User user, Long userId);

    List<Twit> findByLikesContainingOrderByCreatedAtDesc(User user);

    @Query("SELECT t FROM Twit t JOIN t.likes l WHERE l.user.id=:userId")
    List<Twit> findByLikesUser_id(Long userId);

}

