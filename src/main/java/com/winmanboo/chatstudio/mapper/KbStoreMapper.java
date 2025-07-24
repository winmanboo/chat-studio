package com.winmanboo.chatstudio.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.winmanboo.chatstudio.entity.KbStore;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface KbStoreMapper extends BaseMapper<KbStore> {
  
  List<KbStore> getStoreListByUserId(@Param("userId") String userId);
}
