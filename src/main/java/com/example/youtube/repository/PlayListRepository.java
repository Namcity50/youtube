package com.example.youtube.repository;

import com.example.youtube.entity.PlayListEntity;
import com.example.youtube.enums.PlayListEnums;
import com.example.youtube.mapper.PlayListMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface PlayListRepository extends CrudRepository<PlayListEntity,Integer> {
    @Modifying
    @Transactional
    @Query(" update PlayListEntity set status = ?2 where id = ?1 ")
    int updateStatus(Integer id, PlayListEnums enums);
    @Query(value = " select p.id, p.name, p.description,p.status, " +
            " p.order_number, p.channel_id as channelId,c.name as channelName, " +
            " p.profile_id as profileId, pr.name as profileName, " +
            " pr.surname as profileSurname, pr.photo_id as proPhotoId, " +
            " c.photo_id as chanPhotoId from play_list as p inner join channel as c " +
            " on c.id = p.channel_id inner join profile as pr on " +
            " pr.id = p.profile_id ",nativeQuery = true)
    Page<PlayListMapper> findByPlayListIdPagination(Pageable pageable);
}
