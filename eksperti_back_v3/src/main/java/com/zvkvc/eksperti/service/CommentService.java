package com.zvkvc.eksperti.service;

import com.zvkvc.eksperti.dao.CommentRepository;
import com.zvkvc.eksperti.dao.PostRepository;
import com.zvkvc.eksperti.dao.UserRepository;
import com.zvkvc.eksperti.dto.CommentDto;
import com.zvkvc.eksperti.exceptions.PostNotFoundException;
import com.zvkvc.eksperti.mappings.CommentMapper;
import com.zvkvc.eksperti.model.Comment;
import com.zvkvc.eksperti.model.Post;
import com.zvkvc.eksperti.model.User;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
@AllArgsConstructor
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthService authService;
    @Autowired
    private CommentMapper commentMapper;


    public void save(CommentDto commentDto) {
        Post post = postRepository.findById(commentDto.getPostId())
                .orElseThrow(()-> new PostNotFoundException(commentDto.getPostId().toString()));
        // after we get the post in which comment is to be posted, we need to determine author of the comment
        // for that we need current logged in user which we'll get from authService
        User currentUser = authService.getCurrentUser();
        Comment comment = commentMapper.map(commentDto, post, currentUser);
        commentRepository.save(comment);

    }

    public List<CommentDto> getAllCommentsForPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(()->new PostNotFoundException(postId.toString())); 
        // returns an Optional
        // we need to convert it to stream in order to convert elements from model to dto and then back to list of dto's
        List<Comment> commments = commentRepository.findByPost(post);

        
        return   commentRepository.findByPost(post)
                .stream()
                .map(commentMapper::mapToDto)
                .collect(Collectors.toList());
        
        /*
        List<CommentDto> commentDtos = new ArrayList<CommentDto>();

        return commentDtos;
        */
    }

    public List<CommentDto> getAllCommentsForUser(String userName) {

        System.out.println("Searching for user object via " + userName + " username");
        User user = userRepository.findByUsername(userName)
                .orElseThrow(() -> new UsernameNotFoundException("User by the name: " + userName + " has not been found."));
        System.out.println("User found: OK");

        System.out.println("Searching for comments by " + userName + " .....");
        List<Comment> comments = commentRepository.findAllByUser(user);
        System.out.println("OK. There may be some comments...");

        System.out.println("Comments are: " +comments); // nothing?

        return comments.stream()
                     .map(commentMapper::mapToDto)
                     .collect(Collectors.toList());
                    // seems mapToDto is OK now

       /*
        return commentRepository.findAllByUser(user)
                .stream()
                .map(commentMapper::mapToDto)
                .collect(Collectors.toList());
       */
    }

}