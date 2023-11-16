package toy.five.triprecord.domain.comment.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import toy.five.triprecord.domain.comment.entity.Comment;
import toy.five.triprecord.domain.comment.service.CommentService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class CommentRepositoryTest {

    @InjectMocks
    private CommentService commentService;

    @Mock
    private CommentRepository commentRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void saveTest() {
        Comment comment = new Comment();
        when(commentRepository.save(any(Comment.class))).thenReturn(comment);

        Comment savedComment = commentRepository.save(comment);

        assertEquals(comment, savedComment);
    }

    @Test
    public void findByIdTest() {
        Comment comment = new Comment();
        when(commentRepository.findById(any(Long.class))).thenReturn(Optional.of(comment));

        Optional<Comment> foundComment = commentRepository.findById(1L);

        assertEquals(Optional.of(comment), foundComment);
    }

    @Test
    public void getCommentByTripOrderByIdTest() {
        Comment comment1 = new Comment();
        Comment comment2 = new Comment();
        List<Comment> comments = Arrays.asList(comment1, comment2);
        when(commentRepository.getCommentByTripOrderById(any())).thenReturn(comments);

        List<Comment> foundComments = commentRepository.getCommentByTripOrderById(any());

        assertEquals(comments, foundComments);
    }

    @Test
    public void findByTripIdAndIdTest() {
        Comment comment = new Comment();
        when(commentRepository.findByTripIdAndId(any(Long.class), any(Long.class))).thenReturn(Optional.of(comment));

        Optional<Comment> foundComment = commentRepository.findByTripIdAndId(1L, 1L);

        assertEquals(Optional.of(comment), foundComment);
    }
}
