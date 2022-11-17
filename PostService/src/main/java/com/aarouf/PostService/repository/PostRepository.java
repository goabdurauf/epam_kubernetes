package com.aarouf.PostService.repository;

import com.aarouf.PostService.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post,Long> {
}
