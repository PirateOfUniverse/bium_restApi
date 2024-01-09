package com.community.app.controller;

import com.community.app.domain.Post;
import com.community.app.repository.BoardRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@RunWith(SpringRunner.class)
public class BoardControllerTest {

    @Autowired
    MockMvc mockMvc; // 가짜 요청을 보내서 디스패처 서블릿에게 보내고 응답이 가능해짐

    @Autowired
    ObjectMapper objectMapper;

    // @WebMvcTest는 웹용 빈들만 등록을 해주기때문에
    // 리포지토리를 빈으로 등록하려면 필수적으로 해야함
    @MockBean
    BoardRepository boardRepository; // 리포지토리 목킹

    @Test
    public void writePost() throws Exception {
        Post post = Post.builder()
                .title("안녕하세요")
                .content("반가워요오오오옹~~~")
                .build();
        post.setPidx(1);
        Mockito.when(boardRepository.save(post)).thenReturn(post);
        // boardRepository에서 save가 실행이되었을때 post를 리턴하세요 라는뜻

        mockMvc.perform(post("/api/write/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaTypes.HAL_JSON)
                        .content(objectMapper.writeValueAsString(post)))
                .andDo(print()) // 콘솔에서 어떤 요청과 응답을 받았는지 볼 수 있음
                .andExpect(status().isCreated())
                .andExpect(jsonPath("pidx").exists());
    }

}