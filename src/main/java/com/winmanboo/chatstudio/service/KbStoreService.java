package com.winmanboo.chatstudio.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.winmanboo.chatstudio.domain.KbItemVO;
import com.winmanboo.chatstudio.entity.KbStore;
import com.winmanboo.chatstudio.enums.UploadFileStatus;

import java.util.List;

public interface KbStoreService extends IService<KbStore> {
  
  /**
   * 保存知识库存储记录
   *
   * @param fileName 文件名
   * @param extName  文件后缀名
   * @param key      对象存储 objectKey
   * @param kbId     知识库 id
   * @return 知识库存储记录
   */
  KbStore saveDoc(String fileName, String extName, String key, Long kbId);
  
  /**
   * 更新存储状态
   *
   * @param id               存储 id
   * @param uploadFileStatus 文件状态
   */
  void updateStatus(Long id, UploadFileStatus uploadFileStatus);
  
  List<KbItemVO> getKbItemList(String userId);
}
