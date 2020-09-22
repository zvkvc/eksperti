package com.zvkvc.eksperti.dto;

import com.zvkvc.eksperti.model.VoteType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoteDto {
	private VoteType voteType; // enum with values UPVOTE(1) and DOWNVOTE(-1)
	private Long postId;
}