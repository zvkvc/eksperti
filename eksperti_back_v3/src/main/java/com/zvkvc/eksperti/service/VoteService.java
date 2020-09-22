package com.zvkvc.eksperti.service;

import com.zvkvc.eksperti.dao.PostRepository;
import com.zvkvc.eksperti.dao.VoteRepository;
import com.zvkvc.eksperti.dto.VoteDto;
import com.zvkvc.eksperti.exceptions.PostNotFoundException;
import com.zvkvc.eksperti.exceptions.GeneralException;
import com.zvkvc.eksperti.model.Post;
import com.zvkvc.eksperti.model.User;
import com.zvkvc.eksperti.model.Vote;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.zvkvc.eksperti.model.VoteType.UPVOTE;

@Service
@AllArgsConstructor
public class VoteService {

    @Autowired
    private VoteRepository voteRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private AuthService authService;

    @Transactional
    public void vote(VoteDto voteDto) {
        Post post = postRepository.findById(voteDto.getPostId())
                .orElseThrow(() -> new PostNotFoundException("Post Not Found with ID " + voteDto.getPostId()));
        User currentUser = authService.getCurrentUser();
        Optional<Vote> voteByPostAndUser = voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post, currentUser);

        // if user already upvoted he shouldn't be able to upvote again (same for downvote)
        if (voteByPostAndUser.isPresent() && voteByPostAndUser.get().getVoteType().equals(voteDto.getVoteType())) {
            throw new GeneralException("You have already " + voteDto.getVoteType() + "'d this post");
        }
        if (UPVOTE.equals(voteDto.getVoteType())) {
            post.setVoteCount(post.getVoteCount() + 1); // upvote case
        } else {
            post.setVoteCount(post.getVoteCount() - 1); // downvote case
        }
        voteRepository.save(mapToVote(voteDto, post));
        postRepository.save(post);
    }

    private Vote mapToVote(VoteDto voteDto, Post post) {
        return Vote.builder()
                .voteType(voteDto.getVoteType())
                .post(post)
                .user(authService.getCurrentUser())
                .build();
    }


}
