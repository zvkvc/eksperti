package com.zvkvc.eksperti.mappings;

import com.github.marlonlom.utilities.timeago.TimeAgo;
import com.zvkvc.eksperti.dao.CommentRepository;
import com.zvkvc.eksperti.dao.VoteRepository;
import com.zvkvc.eksperti.dto.PostRequest;
import com.zvkvc.eksperti.dto.PostResponse;
import com.zvkvc.eksperti.model.Post;
import com.zvkvc.eksperti.model.Subforum;
import com.zvkvc.eksperti.model.User;
import com.zvkvc.eksperti.service.AuthService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;


@Mapper(componentModel = "spring")
public abstract class PostMapper {  // changed to abstract class because we have some non-abstract methods at the end
                                    // we also autowire some private variables
                                    // both of which we couldn't do with interface...

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private VoteRepository voteRepository;
    @Autowired
    private AuthService authService;

    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "description", source = "postRequest.description")
    @Mapping(target = "subforum", source = "subforum")
    @Mapping(target = "voteCount", constant = "0")
    public abstract Post map(PostRequest postRequest, Subforum subforum, User user);

    @Mapping(target = "id", source = "postId")
    @Mapping(target = "subforumName", source = "subforum.name")
    @Mapping(target = "userName", source = "user.username")
    @Mapping(target = "commentCount", expression = "java(commentCount(post))")
    @Mapping(target = "duration", expression = "java(getDuration(post))")
    public abstract PostResponse mapToDto(Post post);

    Integer commentCount(Post post) {
        return commentRepository.findByPost(post).size();
    }

    String getDuration(Post post) {
        return TimeAgo.using(post.getCreatedDate().toEpochMilli());
    }
}

/*
Old implementation
@Mapper(componentModel = "spring") // register mapper as a component
public interface PostMapper {

    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    */
/*@Mapping(target = "subforum", source = "subforum")
    @Mapping(target = "user", source="user")*//*

    @Mapping(target = "description", source="postRequest.description")
    Post map(PostRequest postRequest, subforum subforum, User user);

    @Mapping(target = "id", source="postId")
    */
/*@Mapping(target = "postName", source= "postName")
    @Mapping(target = "description", source= "description")
    @Mapping(target = "url", source="url")*//*

    @Mapping(target = "subforumName", source="subforum.name")
    @Mapping(target = "userName", source="user.username")
    PostResponse mapToDto(Post post);


}
*/

