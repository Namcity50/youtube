package com.example.youtube.repository;

import com.example.youtube.entity.PlayListVideoEntity;
import com.example.youtube.mapper.PlaylistVideoInfoMapper;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PlayListVideoRepository extends CrudRepository<PlayListVideoEntity,Integer> {
    PlayListVideoEntity findByPlayListIdAndVideoId(Integer pid,String vid);
    @Query(value = "select p.playList_id as playListId, p.video_id as videoId " +
            " v.preview_attach_id as previewId, v.title as videoTitle," +
            " c.id as channelId, c.name as channelName, " +
            " p.created_date as createdDate, p.orden_num as orderNum " +
            "  from play_list_video as p inner join " +
            " video as v on v.id = p.video_id inner join " +
            " channel as c on c.id = v.channel_id ",nativeQuery = true)
    List<PlaylistVideoInfoMapper> findByPlayListId(Integer id);
}
