package com.company.repository;

import com.company.entity.LikeEntity;
import com.company.entity.ProfileEntity;
import com.company.enums.LikeStatus;
import com.company.enums.LikeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeRepository extends JpaRepository<LikeEntity, Integer> {

    @Query("select count(l) from LikeEntity l where l.actionId=?1 and l.type=?2 and l.status=?3")
    Integer likeOrDislikeCount(Integer actionId, LikeType type, LikeStatus status);

    List<LikeEntity> findByProfile(ProfileEntity profileEntity);
}
