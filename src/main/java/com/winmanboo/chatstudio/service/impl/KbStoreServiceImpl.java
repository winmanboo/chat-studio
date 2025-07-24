package com.winmanboo.chatstudio.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.winmanboo.chatstudio.config.OssProperties;
import com.winmanboo.chatstudio.domain.KbItemVO;
import com.winmanboo.chatstudio.entity.KbStore;
import com.winmanboo.chatstudio.enums.UploadFileStatus;
import com.winmanboo.chatstudio.mapper.KbStoreMapper;
import com.winmanboo.chatstudio.converter.KbStoreConverter;
import com.winmanboo.chatstudio.service.KbStoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class KbStoreServiceImpl extends ServiceImpl<KbStoreMapper, KbStore> implements KbStoreService {
  
  private final OssProperties ossProperties;
  
  private final KbStoreConverter kbStoreConverter;
  
  @Override
  public KbStore saveDoc(String fileName, String extName, String key, Long kbId) {
    OssProperties.Doc doc = ossProperties.getDoc();
    String path = doc.getBucketName() + "/" + key;
    KbStore kbStore = new KbStore();
    kbStore.setFileName(fileName);
    kbStore.setExtName(extName);
    kbStore.setKbId(kbId);
    kbStore.setUploadPath(path);
    kbStore.setUploadStatus(UploadFileStatus.UPLOADING.getCode());
    save(kbStore);
    return kbStore;
  }
  
  @Override
  public void updateStatus(Long id, UploadFileStatus uploadFileStatus) {
    KbStore kbStore = new KbStore();
    kbStore.setId(id);
    kbStore.setUploadStatus(uploadFileStatus.getCode());
    updateById(kbStore);
  }
  
  @Override
  public List<KbItemVO> getKbItemList(String userId) {
    List<KbStore> kbStoreList = baseMapper.getStoreListByUserId(userId);
    return kbStoreConverter.toVoList(kbStoreList);
  }
}
