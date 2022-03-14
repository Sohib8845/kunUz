package com.company.repository;

import com.company.entity.ProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Optional;

public interface ProfileRepository extends JpaRepository<ProfileEntity, Integer> {

    Optional<ProfileEntity> findByLoginAndPswd(String login, String pswd);

    Optional<ProfileEntity> findByLogin(String login);

    Optional<ProfileEntity> findByEmail(String email);


    @Transactional
    @Modifying
    @Query("update ProfileEntity p set p.name=:name,p.surname=:surname," +
            "p.email=:email,p.login=:login,p.pswd=:pswd where p.id=:id")
    void update(@Param("name") String name, @Param("surname") String surname, @Param("email") String email,
                @Param("login") String login, @Param("pswd") String pswd, @Param("id") Integer id);
}
