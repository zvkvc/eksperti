package com.zvkvc.eksperti.dao;

import com.zvkvc.eksperti.model.Post;
import com.zvkvc.eksperti.model.Subforum;
import com.zvkvc.eksperti.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllBysubforum(Subforum subforum);

    List<Post> findByUser(User user);
    // where in <Post, Long> -- Post is our entity and Long is type of our primary key

}
