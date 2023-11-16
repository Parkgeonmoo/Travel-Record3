package toy.five.triprecord.domain.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toy.five.triprecord.domain.comment.entity.Comment;
import toy.five.triprecord.domain.trip.entity.Trip;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> getCommentByTripOrderById(Trip trip);

    Optional<Comment> findByTripIdAndId(Long tripId, Long id);
}
