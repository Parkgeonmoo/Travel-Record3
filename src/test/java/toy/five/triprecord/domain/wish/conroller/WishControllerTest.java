package toy.five.triprecord.domain.wish.conroller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import toy.five.triprecord.domain.trip.dto.response.TripCreateResponse;
import toy.five.triprecord.domain.user.dto.response.UserCreateResponse;

import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class WishControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserSetUp userSetUp;

    @Autowired
    private TripSetUp tripSetUp;

    protected MediaType contentType =
            new MediaType(MediaType.APPLICATION_JSON.getType(),
                    MediaType.APPLICATION_JSON.getSubtype(),
                    StandardCharsets.UTF_8);

    @DisplayName("여행을 좋아요 한 후 좋아요 취소까지의 성공 여부 통합 테스트")
    @Test
    void wishTrip_then_unWishTrip_success() throws Exception {

        //given
        UserCreateResponse userCreateResponse = userSetUp.saveUser();
        TripCreateResponse tripCreateResponse = tripSetUp.saveTrip();

        Long beforeWishCount = tripCreateResponse.getWishCount();
        Long afterWishCount = beforeWishCount + 1;

        String postUrl = "/wishes/" + tripCreateResponse.getId() + "/" + userCreateResponse.getId();
        String deleteUrl = "/wishes/" + tripCreateResponse.getId() + "/" + userCreateResponse.getId();

        //when
        //then
        mvc.perform(post(postUrl).contentType(contentType))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value(afterWishCount));

        mvc.perform(delete(deleteUrl).contentType(contentType))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value(beforeWishCount));


    }

}

