package com.gumlayudong.gumlayudongbackend.tag;

import com.gumlayudong.gumlayudongbackend.post.domain.Post;
import com.gumlayudong.gumlayudongbackend.post.domain.PostTag;
import com.gumlayudong.gumlayudongbackend.user.domain.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Tag {

    private Long id;
    private String name;
    private List<PostTag> postTags = new ArrayList<>();

    public Tag(String name) {
        this(null, name);
    }

    public Tag(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
