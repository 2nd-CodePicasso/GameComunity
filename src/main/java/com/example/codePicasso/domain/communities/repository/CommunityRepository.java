package com.example.codePicasso.domain.communities.repository;

import com.example.codePicasso.domain.communities.entity.Community;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunityRepository extends JpaRepository<Community, Long> {
}
