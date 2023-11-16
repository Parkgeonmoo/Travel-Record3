package toy.five.triprecord.domain.comment.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import toy.five.triprecord.domain.comment.entity.Comment;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CommentResponse {

    private Long id;
    private String comment;
    private String nickname;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private Long userId;
    private Long tripId;

    public static CommentResponse fromEntity(Comment comment) {
        return CommentResponse.builder()
            .id(comment.getId())
            .comment(comment.getComment())
            .createdAt(comment.getCreatedAt())
            .modifiedAt(comment.getModifiedAt())
            .nickname(comment.getUser().getName())
            .userId(comment.getUser().getId())
            .tripId(comment.getTrip().getId())
            .build();
    }
}
