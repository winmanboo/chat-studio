package com.winmanboo.chatstudio.converter;

import com.winmanboo.chatstudio.domain.KbItemVO;
import com.winmanboo.chatstudio.entity.KbStore;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface KbStoreConverter {
  
  @Mapping(source = "fileName", target = "name")
  KbItemVO toVo(KbStore kbStore);
  
  List<KbItemVO> toVoList(List<KbStore> storeList);
}
