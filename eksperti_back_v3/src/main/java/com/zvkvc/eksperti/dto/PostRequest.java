package com.zvkvc.eksperti.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostRequest {
    private Long postId;
    private String subforumName; // mapped from subforum field
    private String postName;
    private String url;
    private String description;

}
