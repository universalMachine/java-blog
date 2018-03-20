package com.wang.blog.web;

import com.wang.blog.DTO.UserDTO;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.springframework.http.MediaType;


import java.util.Scanner;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RegisterControllerTest extends BaseTest{

    @Test
    public void register() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserName("wang8");
        userDTO.setPassword("232313");

        String userDTOJson = objectMapper.writeValueAsString(userDTO);
        System.out.println(userDTOJson);
      mockMvc.perform(post("/register")
             .contentType(MediaType.APPLICATION_JSON)
            .content(userDTOJson))
             .andExpect(status().is2xxSuccessful())
      .andExpect( jsonPath("returnCode", Matchers.is(7000)));


    }

    @Test
    public void testScanner(){
        Scanner keyboard = new Scanner(System.in);
        System.out.println(keyboard.next());

    }


}