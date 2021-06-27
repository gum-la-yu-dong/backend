package gumrya.like.domain.repository;

import gumrya.like.domain.model.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {
}

