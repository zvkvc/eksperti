package com.zvkvc.eksperti.model;

import com.zvkvc.eksperti.exceptions.GeneralException;

import java.util.Arrays;


public enum VoteType {
    UPVOTE(1), DOWNVOTE(-1), // two possible values
    ;

    private int direction;

    VoteType(int direction) {
    }

    public static VoteType lookup(Integer direction) {
        return Arrays.stream(VoteType.values())
                .filter(value -> value.getDirection().equals(direction))
                .findAny()
                .orElseThrow(() -> new GeneralException("Vote not found"));
        
    }

    public Integer getDirection() {
        return direction;
    }
}
