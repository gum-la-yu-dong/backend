package gumrya.tag.domain.repository;

import gumrya.tag.domain.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
}
