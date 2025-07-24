package com.winmanboo.chatstudio.converter;

import com.winmanboo.chatstudio.domain.SessionVO;
import com.winmanboo.chatstudio.entity.Session;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface SessionConverter {

  @Mapping(source = "title", target = "sessionTitle")
  SessionVO toVo(Session session);

  List<SessionVO> toVoList(List<Session> sessions);
}
