package com.example.youtube.repository;
import com.example.youtube.entity.ProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<ProfileEntity,Integer> {
    @Query("from  ProfileEntity where email = ?1 ")
    Optional<ProfileEntity> findByEmail(String username);

    Optional<ProfileEntity> findByEmailAndPasswordAndVisible(String login, String md5Hash, boolean b);
    @Modifying
    @Transactional
    @Query("update ProfileEntity set password =?2 where id = ?1 ")
    int updatePassword(Integer id, String pass);
    @Modifying
    @Transactional
    @Query("update ProfileEntity set email =?2 where id = ?1 ")
    ProfileEntity updateEmail(Integer id, String email);
    @Modifying
    @Transactional
    @Query("update ProfileEntity set name =?2, surname = ?3 where id = ?1 ")
    int updateNameAndSurname(Integer id, String name, String surname);
//    @Modifying
//    @Transactional
//    @Query("update ProfileEntity set attachId =?1")
//    int updateAttach(String id);
}
