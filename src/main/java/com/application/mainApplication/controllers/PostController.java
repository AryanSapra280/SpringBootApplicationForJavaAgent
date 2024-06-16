package com.application.mainApplication.controllers;

import com.application.mainApplication.entities.HttpOutBound;
import com.application.mainApplication.entities.Post;
import com.application.mainApplication.entities.Response;
import com.application.mainApplication.services.PostService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;




@RestController
@RequestMapping("/api")
public class PostController {

    @Autowired
    private PostService postService;
    private static ObjectMapper objectMapper = new ObjectMapper();
    RestTemplate restTemplate = new RestTemplate();
    // @PostMapping("/createNewPost")
    // public ResponseEntity<?> savePost(@RequestBody Post post) {
    // //    return ResponseEntity.status(HttpStatus.OK).body(postService.testAgent());
    //     Integer savedPostId = postService.savePost(post);
    //     return ResponseEntity.status(HttpStatus.OK).body(String.format("Post created successfully with an id = %d ",savedPostId));
    // }
    @PostMapping("/createNewPost")
    public String savePost(@RequestBody String postString) {
        Post post;
        try {
            post = objectMapper.readValue(postString, Post.class);

            // Get the response as a string
            String httpOutboundString = restTemplate.getForEntity("https://mocki.io/v1/f5dee2f0-9931-4301-bd22-aa981b588254", String.class).getBody();

            // Directly deserialize the string to HttpOutBound class
            HttpOutBound httpOutBound = objectMapper.readValue(httpOutboundString, HttpOutBound.class);

            // Save the post
            Post savedPost = postService.savePost(post);

            // Create a response object
            Response response = new Response(savedPost, httpOutBound);

            // Serialize the response object to a string
            String responseInString = objectMapper.writeValueAsString(response);

            // Return the response entity
            return responseInString.toString();
        } catch (JsonMappingException e) {
            return e.getMessage();
        } catch (JsonProcessingException e) {
            return e.getMessage();
        }
    }


    @GetMapping("/allPosts")
    public ResponseEntity<?> getAllPosts() {
        return ResponseEntity.status(HttpStatus.OK).body(postService.getAllPosts());
    }


}
