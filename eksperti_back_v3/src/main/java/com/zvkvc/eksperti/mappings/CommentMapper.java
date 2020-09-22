package com.zvkvc.eksperti.mappings;

import com.zvkvc.eksperti.dto.CommentDto;
import com.zvkvc.eksperti.model.Comment;
import com.zvkvc.eksperti.model.Post;
import com.zvkvc.eksperti.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
/*
@Mapper(componentModel = "spring") // register mapper as a component
public interface CommentMapper {

    @Mapping(target = "id", ignore = true) // id will be auto-generated whenever we save object to DB
    @Mapping(target = "text", source = "commentDto.text")
    @Mapping(target = "createdDate", expression= "java(java.time.Instant.now())")
    @Mapping(target = "post", source= "post")
    @Mapping(target = "user", source = "user")
    Comment map(CommentDto commentDto, Post post, User user);

    @Mapping(target = "postId", expression = "java(comment.getPost().getPostId())")
    @Mapping(target = "userName", expression = "java(comment.getUser().getUsername())")
    CommentDto mapToDto(Comment comment);

}
*/

@Mapper(componentModel = "spring")
public abstract class CommentMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "text", source = "commentDto.text")
    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "post", source = "post")
    @Mapping(target = "user", source = "user")
    public abstract Comment map(CommentDto commentDto, Post post, User user);

    // mapstruct didn't implement mapToDto correctly for some reason..
    public CommentDto mapToDto(Comment comment) {
    	return CommentDto.builder().id(comment.getId())
    						.postId((comment.getPost()).getPostId())
    						.createdDate(comment.getCreatedDate())
    						.text(comment.getText())
    						.userName((comment.getUser()).getUsername())
    						.build();

    }
}
