package com.gumlayudong.gumlayudongbackend.tag;

import com.gumlayudong.gumlayudongbackend.common.domain.BaseEntity;
import com.gumlayudong.gumlayudongbackend.post.domain.PostTag;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Tag extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    private Long id;

    @OneToMany(mappedBy = "tag", cascade = CascadeType.ALL)
    private final List<PostTag> postTags = new ArrayList<>();

    private String name;

    public Tag(String name) {
        this(null, name);
    }

    public Tag(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
