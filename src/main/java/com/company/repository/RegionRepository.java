package com.company.repository;

import com.company.entity.RegionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface RegionRepository extends JpaRepository<RegionEntity,Integer> {
    @Modifying
    @Query("DELETE FROM CommentEntity c WHERE c.id=?1")
    void DeleteById(Integer id);
}
