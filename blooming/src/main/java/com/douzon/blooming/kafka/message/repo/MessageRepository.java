package com.douzon.blooming.kafka.message.repo;

import com.douzon.blooming.kafka.message.dto.InsertMessageDto;
import com.douzon.blooming.kafka.message.dto.MessageDto;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MessageRepository {

    @Select("SELECT message_no, send_id, send_name, target_id, target_name, message, send_time FROM project.message WHERE target_name = #{userId} AND message_check = 0")
    List<MessageDto> findMessagesById(String userId);

    @Insert("INSERT INTO project.message(send_id, send_name, target_id, target_name, message, send_time) " +
            "VALUES (#{dto.sendId}, #{dto.sendName}, #{dto.targetId}, #{dto.targetName}, #{dto.message}, #{dto.sendTime})")
    void save(@Param("dto") InsertMessageDto dto);

    @Update("UPDATE project.message SET message_check = 1 WHERE message_no = #{messageNo}")
    void checkMessage(Long messageNo);
}
