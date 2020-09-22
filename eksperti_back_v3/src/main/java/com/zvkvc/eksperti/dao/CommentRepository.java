package com.zvkvc.eksperti.dao;

import com.zvkvc.eksperti.model.Comment;
import com.zvkvc.eksperti.model.Post;
import com.zvkvc.eksperti.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost(Post post);

    List<Comment> findAllByUser(User user);
}
