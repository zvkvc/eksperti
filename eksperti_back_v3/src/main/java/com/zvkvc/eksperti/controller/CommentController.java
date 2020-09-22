package com.zvkvc.eksperti.controller;

import com.zvkvc.eksperti.dto.CommentDto;
import com.zvkvc.eksperti.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/comments/")
@AllArgsConstructor
public class CommentController {

    @Autowired
    CommentService commentService;

    @PostMapping // ("/")
    public ResponseEntity<String> createComment(@RequestBody CommentDto commentDto) {
        commentService.save(commentDto);
        return new ResponseEntity<>("Comment created successfully.", HttpStatus.OK);
    }

    @GetMapping("/by-post/{postId}")
    public ResponseEntity<List<CommentDto>> getAllCommentsForPost(@PathVariable Long postId) {
       return ResponseEntity.status(HttpStatus.OK).body(commentService.getAllCommentsForPost(postId));

    }

    @GetMapping("/by-user/{userName}")
    public ResponseEntity<List<CommentDto>> getCommentsByUsername(@PathVariable String userName) {
        return ResponseEntity.status(HttpStatus.OK).body(commentService.getAllCommentsForUser(userName));

    }





}
