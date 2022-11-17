package com.aarouf.PostService.web;

import com.aarouf.PostService.domain.Post;
import com.aarouf.PostService.dto.PostDto;
import com.aarouf.PostService.dto.UserDto;
import com.aarouf.PostService.repository.PostRepository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/posts")
public class Web {

    private PostRepository postRepository;
    private RestTemplate restTemplate;

    @Value("${user_service.url}")
    private String userServiceUrl;

    @Autowired
    public Web(
            PostRepository postRepository,
            RestTemplate restTemplate
    ) {
        this.postRepository = postRepository;
        this.restTemplate = restTemplate;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    @PostMapping
    public PostDto uploadPost(
            @RequestBody PostDto postDto
    ) throws JsonProcessingException {
        System.out.println("Hello Test");
        UserDto userDto = verifyUser(postDto.getAuthorId());
        Post post = new Post(postDto.getAuthorId(), postDto.getText());
        userDto.setAmountOfPosts(userDto.getAmountOfPosts() + 1);
        updatePostCount(userDto);
        return new PostDto(postRepository.save(post));

    }

    @GetMapping("/{postId}")
    public PostDto getPost(
            @PathVariable("postId") long postId
    ) {
        System.out.println("Test-Test");
        Post post = verifyPost(postId);
        return new PostDto(post);
    }

    @DeleteMapping("/{postId}")
    @Transactional
    @ResponseStatus(HttpStatus.OK)
    public void deletePost(
            @PathVariable("postId") long postId
    ) throws JsonProcessingException {
        Post post = verifyPost(postId);
        UserDto userDto = verifyUser(post.getAuthorId());
        userDto.setAmountOfPosts(userDto.getAmountOfPosts() - 1);
        updatePostCount(userDto);
        postRepository.delete(post);
    }

    @PutMapping("/{postId}")
    public PostDto updatePost(
            @PathVariable("postId") long postId,
            @RequestBody PostDto postDto
    ) {
        Post post = verifyPost(postId);
        post.setText(postDto.getText());
        return new PostDto(postRepository.save(post));
    }

    private UserDto verifyUser(long userId) {
        return restTemplate.getForObject("http://spring-cloud-user-service:9000/users/" + userId, UserDto.class);
    }

    private Post verifyPost(long postId) {
        return postRepository.findById(postId).orElseThrow(() ->
                new NoSuchElementException("post not found"));
    }

    private UserDto updatePostCount(UserDto userDto) throws JsonProcessingException {
        String url = "http://spring-cloud-user-service:9000/users/" + userDto.getId();
        ObjectMapper obj = new ObjectMapper();
        String requestBody = obj.writeValueAsString(userDto);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<String>(requestBody, headers);
        ResponseEntity<UserDto> response = restTemplate.exchange(url, HttpMethod.PUT, entity, UserDto.class);
        return response.getBody();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public String return400(NoSuchElementException ex) {
        return ex.getMessage();

    }

    enum TypeOfIncrement {
        UP,
        DOWN
    }


}
