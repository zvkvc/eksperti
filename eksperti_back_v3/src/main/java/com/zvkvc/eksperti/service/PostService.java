package com.zvkvc.eksperti.service;

import com.zvkvc.eksperti.dao.PostRepository;
import com.zvkvc.eksperti.dao.SubforumRepository;
import com.zvkvc.eksperti.dao.UserRepository;
import com.zvkvc.eksperti.dto.PostRequest;
import com.zvkvc.eksperti.dto.PostResponse;
import com.zvkvc.eksperti.exceptions.PostNotFoundException;
import com.zvkvc.eksperti.exceptions.SubforumNotFoundException;
import com.zvkvc.eksperti.mappings.PostMapper;
import com.zvkvc.eksperti.model.Post;
import com.zvkvc.eksperti.model.Subforum;
import com.zvkvc.eksperti.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@Transactional // VERY IMPORTANT - otherwise JDBC session won't be opened
public class PostService {

    @Autowired
    private SubforumRepository subforumRepository;
    @Autowired
    private AuthService authService;
    @Autowired
    private PostMapper postMapper;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;

    public void save(PostRequest postRequest) {
        Subforum subforum = subforumRepository.findByName(postRequest.getSubforumName())
                .orElseThrow(() -> new SubforumNotFoundException(postRequest.getSubforumName()));
        User currentUser = authService.getCurrentUser();
        /* // does not join subforum entity to post entity in DB, only creates a post
        Post savedPost = postMapper.map(postRequest, subforum, currentUser); // postRequest to Post converter
        postRepository.save(savedPost);
        */
        Post savedPost = postRepository.save(postMapper.map(postRequest, subforum, currentUser));

        subforum.getPosts().add(savedPost);
        subforumRepository.save(subforum);
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getAllPosts() {
        return postRepository.findAll()
                .stream()
                .map(postMapper::mapToDto)
                .collect(toList());
    }

    @Transactional(readOnly = true)
    public PostResponse getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(()-> new PostNotFoundException(id.toString()));
        return postMapper.mapToDto(post);
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostsBySubforum(Long id) {
        Subforum subforum = subforumRepository.findById(id)
                .orElseThrow(() -> new SubforumNotFoundException(id.toString()));
        List<Post> posts = postRepository.findAllBysubforum(subforum);
        return posts.stream()
                .map(postMapper::mapToDto)
                .collect(toList());
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostsByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        return postRepository.findByUser(user)
                .stream()
                .map(postMapper::mapToDto)
                .collect(toList());
    }


}
