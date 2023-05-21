package com.example.youtube.repository;

import com.example.youtube.entity.PlayListEntity;
import com.example.youtube.enums.PlayListEnums;
import com.example.youtube.mapper.PlayListInfoMapper;
import com.example.youtube.mapper.PlayListShortInfoMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PlayListRepository extends CrudRepository<PlayListEntity, Integer> {
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
            " pr.id = p.profile_id ", nativeQuery = true)
    Page<PlayListInfoMapper> findByPlayListIdPagination(Pageable pageable);

    @Query(value = " select p.id, p.name, p.description,p.status, " +
            " p.order_number, p.channel_id as channelId,c.name as channelName, " +
            " p.profile_id as profileId, pr.name as profileName, " +
            " pr.surname as profileSurname, pr.photo_id as proPhotoId, " +
            " c.photo_id as chanPhotoId from play_list as p inner join channel as c " +
            " on c.id = p.channel_id inner join profile as pr on " +
            " pr.id = p.profile_id where p.profile_id = ?1 order by p.order_number desc ", nativeQuery = true)
    List<PlayListInfoMapper> getListByProfileId(Integer id);

    @Query(value = " select  p.id,p.name,p.created_date," +
            " c.id as channelId,c.name = channelName," +
            " count(v.id) as videoCount, v.id as videId," +
            " v.name as videoName,a.duration as duration " +
            " from play_list as p " +
            " inner join channel as c on c.id = p.channel_id inner join " +
            " video as v on v.channel_id = c.id inner join " +
            " attach as a on a.id = c.photo_id where c.channel_id = ?1 " +
            " order by p.order_number desc limit 2 ", nativeQuery = true)
    List<PlayListShortInfoMapper> getUserList(Integer id);
    @Query(value = " select  p.id,p.name,p.created_date," +
            " c.id as channelId,c.name = channelName," +
            " count(v.id) as videoCount, v.id as videId," +
            " v.name as videoName,a.duration as duration " +
            " from play_list as p " +
            " inner join channel as c on c.id = p.channel_id inner join " +
            " video as v on v.channel_id = c.id inner join " +
            " attach as a on a.id = c.photo_id where c.channel_id = ?1 and p.status = ?2 " +
            " order by p.order_number desc limit 2 ", nativeQuery = true)
    PlayListShortInfoMapper getChannelPlayList(String id, PlayListEnums enums);
    @Query(value = " select  p.id,p.name, count(v.id) as videoCount, " +
            " v.view_count as viewCount, a.updated_date as updatedDate " +
            " from play_list as p " +
            " inner join channel as c on c.id = p.channel_id inner join " +
            " video as v on v.channel_id = c.id inner join " +
            " attach as a on a.id = c.photo_id where c.channel_id = ?1 " +
            " order by a.updated_date asc  ", nativeQuery = true)
    PlayListShortInfoMapper getByPlayListId(Integer id);
}
