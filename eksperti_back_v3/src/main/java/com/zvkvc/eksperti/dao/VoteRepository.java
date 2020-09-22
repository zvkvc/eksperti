package com.zvkvc.eksperti.dao;

import com.zvkvc.eksperti.model.Post;
import com.zvkvc.eksperti.model.User;
import com.zvkvc.eksperti.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
    Optional<Vote> findTopByPostAndUserOrderByVoteIdDesc(Post post, User currentUser);
}
