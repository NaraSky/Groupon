package com.lb.infrastructure.adapter.repository;

import com.lb.domain.tag.adapter.repository.ITagRepository;
import com.lb.domain.tag.model.entity.CrowdTagsJobEntity;
import com.lb.infrastructure.dao.ICrowdTagsDao;
import com.lb.infrastructure.dao.ICrowdTagsDetailDao;
import com.lb.infrastructure.dao.ICrowdTagsJobDao;
import com.lb.infrastructure.dao.po.CrowdTags;
import com.lb.infrastructure.dao.po.CrowdTagsDetail;
import com.lb.infrastructure.dao.po.CrowdTagsJob;
import com.lb.infrastructure.redis.IRedisService;
import org.redisson.api.RBitSet;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository
public class TagRepository implements ITagRepository {
    @Resource
    private ICrowdTagsDao crowdTagsDao;
    @Resource
    private ICrowdTagsJobDao crowdTagsJobDao;
    @Resource
    private ICrowdTagsDetailDao crowdTagsDetailDao;
    @Resource
    private IRedisService redisService;

    @Override
    public CrowdTagsJobEntity queryCrowdTagsJobEntity(String tagId, String batchId) {
        CrowdTagsJob crowdTagsJob = new CrowdTagsJob();
        crowdTagsJob.setTagId(tagId);
        crowdTagsJob.setBatchId(batchId);

        CrowdTagsJob crowdTagsJobRes = crowdTagsJobDao.queryCrowdTagsJob(crowdTagsJob);
        if (crowdTagsJobRes == null) return null;
        return CrowdTagsJobEntity.builder()
                .tagType(crowdTagsJobRes.getTagType())
                .tagRule(crowdTagsJobRes.getTagRule())
                .statStartTime(crowdTagsJobRes.getStatStartTime())
                .statEndTime(crowdTagsJobRes.getStatEndTime())
                .build();
    }

    @Override
    public void addCrowdTagsUserId(String tagId, String userId) {
        CrowdTagsDetail crowdTagsDetail = new CrowdTagsDetail();
        crowdTagsDetail.setTagId(tagId);
        crowdTagsDetail.setUserId(userId);

        try {
            crowdTagsDetailDao.addCrowdTagsUserId(crowdTagsDetail);

            RBitSet bitSet = redisService.getBitSet(tagId);
            bitSet.set(redisService.getIndexFromUserId(userId), true);
        } catch (DuplicateKeyException ignore) {
            // 忽略唯一索引冲突
        }
    }

    @Override
    public void updateCrowdTagsStatistics(String tagId, int count) {
        CrowdTags crowdTagsReq = new CrowdTags();
        crowdTagsReq.setTagId(tagId);
        crowdTagsReq.setStatistics(count);
        crowdTagsDao.updateCrowdTagsStatistics(crowdTagsReq);
    }
}
