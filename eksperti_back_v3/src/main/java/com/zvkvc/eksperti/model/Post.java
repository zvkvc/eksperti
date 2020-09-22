package com.zvkvc.eksperti.model;

import com.sun.istack.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.Instant;

@Data // Lombok data annotation for getters and setters
@Entity
@Builder // Lombok annotation for Builder design pattern
@AllArgsConstructor
@NoArgsConstructor
public class Post {
   
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId; 

    @NotBlank(message = "Post Name cannot be empty or Null") // hibernate validator annotation
    private String postName; 

    @Nullable
    private String url;

    @Nullable
    @Lob // database should store the property as Large Object (BLOB, CLOB..)
    private String description;

    private Integer voteCount = 0; 

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private User user;

    private Instant createdDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subforumId", referencedColumnName = "id")
    private Subforum subforum;
}

