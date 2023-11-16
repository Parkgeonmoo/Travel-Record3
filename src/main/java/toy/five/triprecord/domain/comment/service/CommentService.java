package toy.five.triprecord.domain.comment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toy.five.triprecord.domain.comment.dto.request.CommentRequest;
import toy.five.triprecord.domain.comment.dto.response.CommentResponse;
import toy.five.triprecord.domain.comment.entity.Comment;
import toy.five.triprecord.domain.comment.repository.CommentRepository;
import toy.five.triprecord.domain.trip.entity.Trip;
import toy.five.triprecord.domain.trip.repository.TripRepository;
import toy.five.triprecord.domain.user.entity.User;
import toy.five.triprecord.domain.user.repository.UserRepository;
import toy.five.triprecord.global.exception.BaseException;
import toy.five.triprecord.global.exception.ErrorCode;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final TripRepository tripRepository;

    @Transactional
    public Long save(Long tripId, String nickname, CommentRequest request) {
        User user = userRepository.findByName(nickname);
        Trip trip = tripRepository.findById(tripId).orElseThrow(() ->
            new BaseException(ErrorCode.TRIP_NO_EXIST));

        Comment newComment = Comment.builder()
            .comment(request.getComment())
            .user(user)
            .trip(trip)
            .build();
        commentRepository.save(newComment);

        return newComment.getId();
    }

    @Transactional(readOnly = true)
    public List<CommentResponse> findAll(Long tripId) {
        Trip trip = tripRepository.findById(tripId).orElseThrow(() ->
            new BaseException(ErrorCode.TRIP_NO_EXIST));
        List<Comment> comments = trip.getComments();

        return comments.stream().map(CommentResponse::fromEntity)
            .collect(Collectors.toList());
    }

    @Transactional
    public void update(Long tripId, Long id, CommentRequest request) {
        Comment comment = commentRepository.findByTripIdAndId(tripId, id).orElseThrow(() ->
            new BaseException(ErrorCode.COMMENT_NO_EXIST));

        comment.update(request.getComment());
    }

    @Transactional
    public void delete(Long tripId, Long id) {
        Comment comment = commentRepository.findByTripIdAndId(tripId, id).orElseThrow(() ->
            new BaseException(ErrorCode.COMMENT_NO_EXIST));

        commentRepository.delete(comment);
    }
}

