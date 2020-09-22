package com.zvkvc.eksperti.mappings;

import com.zvkvc.eksperti.dto.SubforumDto;
import com.zvkvc.eksperti.model.Post;
import com.zvkvc.eksperti.model.Subforum;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring") // register mapper as a component
public interface SubforumMapper {
    /* --  Define your mappings here --  */

    @Mapping(target = "postCount", expression = "java(mapPosts(subforum.getPosts()))")
            // maps source="posts.size()" to "target=postCount"
    SubforumDto mapSubforumToDto(Subforum subforum);

    default Integer mapPosts(List<Post> posts) { // maps List<Post> to Integer
        return posts.size();
    }

    @InheritInverseConfiguration // inverse mapping
    @Mapping(target = "posts", ignore = true) // we ignore posts field
    Subforum mapDtoToSubforum(SubforumDto subforumDto);
}