package toy.five.triprecord.domain.comment.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.test.context.support.WithMockUser;
import toy.five.triprecord.domain.comment.dto.request.CommentRequest;
import toy.five.triprecord.domain.comment.entity.Comment;
import toy.five.triprecord.domain.comment.repository.CommentRepository;
import toy.five.triprecord.domain.trip.entity.Trip;
import toy.five.triprecord.domain.trip.repository.TripRepository;
import toy.five.triprecord.domain.user.entity.User;
import toy.five.triprecord.domain.user.repository.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

public class CommentServiceTest {

    @InjectMocks
    private CommentService commentService;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TripRepository tripRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @WithMockUser(username = "testUser")
    public void saveTest() {
        Comment comment = new Comment();
        User user = User.builder()
            .name("testUser")
            .build();
        Trip trip = Trip.builder()
            .id(1L)
            .build();
        when(userRepository.findByName(any(String.class))).thenReturn(user);
        when(tripRepository.findById(any(Long.class))).thenReturn(Optional.of(trip));
        when(commentRepository.save(any(Comment.class))).thenReturn(comment);

        Long savedCommentId = commentService.save(1L, "test", CommentRequest.builder()
            .comment("test comment")
            .build());

        assertEquals(comment.getId(), savedCommentId);
    }

    @Test
    public void updateTest() {
        Comment comment = new Comment();
        when(commentRepository.findByTripIdAndId(anyLong(), anyLong())).thenReturn(Optional.of(comment));

        commentService.update(1L, 1L, CommentRequest.builder()
                .comment("updated comment")
                .build());
    }

    @Test
    public void deleteTest() {
        Comment comment = new Comment();
        when(commentRepository.findByTripIdAndId(anyLong(), anyLong())).thenReturn(Optional.of(comment));

        commentService.delete(1L, 1L);
    }
}