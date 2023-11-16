package toy.five.triprecord.domain.comment.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import toy.five.triprecord.domain.comment.dto.request.CommentRequest;
import toy.five.triprecord.domain.comment.dto.response.CommentResponse;
import toy.five.triprecord.domain.comment.service.CommentService;
import toy.five.triprecord.domain.user.service.UserService;
import toy.five.triprecord.global.security.service.JwtTokenService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CommentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommentService commentService;

    @MockBean
    private UserService userService;

    @MockBean
    private JwtTokenService jwtTokenService;

    @Test
    @WithMockUser(username = "testUser")
    public void createCommentTest() throws Exception {
        CommentRequest commentRequest = CommentRequest.builder()
            .comment("test comment")
            .build();
        CommentResponse commentResponse = CommentResponse.builder()
            .id(1L)
            .comment("test comment")
            .nickname("testUser")
            .build();

        when(commentService.save(any(Long.class), any(String.class), any(CommentRequest.class)))
            .thenReturn(1L);

        mockMvc.perform(post("/trips/1/comments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(commentRequest)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data").value(1L));
    }

    @Test
    @WithMockUser(username = "testUser")
    public void getAllCommentsTest() throws Exception {
        CommentResponse commentResponse1 = CommentResponse.builder()
            .id(1L)
            .comment("test comment1")
            .nickname("testUser")
            .build();
        CommentResponse commentResponse2 = CommentResponse.builder()
            .id(2L)
            .comment("test comment2")
            .nickname("testUser")
            .build();

        List<CommentResponse> comments = Arrays.asList(commentResponse1, commentResponse2);

        when(commentService.findAll(any(Long.class))).thenReturn(comments);

        mockMvc.perform(get("/trips/1/comments")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data", hasSize(2)))
            .andExpect(jsonPath("$.data[0].comment").value("test comment1"))
            .andExpect(jsonPath("$.data[1].comment").value("test comment2"));
    }
}
