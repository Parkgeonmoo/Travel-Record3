package toy.five.triprecord.domain.wish.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import toy.five.triprecord.domain.wish.service.WishService;
import toy.five.triprecord.global.common.StatusCode;
import toy.five.triprecord.global.exception.ApiResponse;

import java.security.Principal;

@Slf4j
@RestController
@RequiredArgsConstructor
public class WishController {

    private final WishService wishService;

    @PostMapping("wishes/{tripId}")
    public ResponseEntity<ApiResponse> wishTrip(@PathVariable Long tripId, Principal principal) {
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .status(String.valueOf(StatusCode.SUCCESS))
                        .code(HttpStatus.OK.value())
                        .data(wishService.saveWish(principal.getName(), tripId))
                        .build()
        );
    }

    @DeleteMapping("wishes/{tripId}")
    public ResponseEntity<ApiResponse> unWishTrip(@PathVariable Long tripId, Principal principal) {
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .status(String.valueOf(StatusCode.SUCCESS))
                        .code(HttpStatus.OK.value())
                        .data(wishService.deleteWish(principal.getName(), tripId))
                        .build()
        );
    }

}
