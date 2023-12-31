package toy.five.triprecord.domain.comment.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import toy.five.triprecord.domain.comment.dto.request.CommentRequest;
import toy.five.triprecord.domain.comment.service.CommentService;
import toy.five.triprecord.global.common.StatusCode;
import toy.five.triprecord.global.exception.ApiResponse;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/trips")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{tripId}/comments")
    public ResponseEntity<ApiResponse> createComment(@PathVariable Long tripId, @Valid @RequestBody CommentRequest request,
                                                     @AuthenticationPrincipal UserDetails userDetails
    ) {

        return ResponseEntity.ok(
            ApiResponse.builder()
                .status(String.valueOf(StatusCode.SUCCESS))
                .code(HttpStatus.OK.value())
                .data(commentService.save(tripId, userDetails.getUsername(), request))
                .build()
        );
    }

    @GetMapping("/{tripId}/comments")
    public ResponseEntity<ApiResponse> read(@PathVariable Long tripId) {

        return ResponseEntity.ok(
            ApiResponse.builder()
                .status(String.valueOf(StatusCode.SUCCESS))
                .code(HttpStatus.OK.value())
                .data(commentService.findAll(tripId))
                .build()
        );
    }

    @PutMapping({"/{tripId}/comments/{id}"})
    public ResponseEntity<ApiResponse> update(@PathVariable Long tripId, @PathVariable Long id,
                                              @Valid @RequestBody CommentRequest request
    ) {
        commentService.update(tripId, id, request);

        return ResponseEntity.ok(
            ApiResponse.builder()
                .status(String.valueOf(StatusCode.SUCCESS))
                .code(HttpStatus.OK.value())
                .data(id)
                .build()
        );
    }

    @DeleteMapping("/{tripId}/comments/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable Long tripId, @PathVariable Long id) {
        commentService.delete(tripId, id);

        return ResponseEntity.ok(
            ApiResponse.builder()
                .status(String.valueOf(StatusCode.SUCCESS))
                .code(HttpStatus.OK.value())
                .data(id)
                .build()
        );
    }
}
