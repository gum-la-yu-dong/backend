package gumrya.post.domain.repository;

import gumrya.post.domain.model.PostTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostTagRepository extends JpaRepository<PostTag, Long> {
}
