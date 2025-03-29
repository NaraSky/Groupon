package com.lb.infrastructure.dao;

import com.lb.infrastructure.dao.po.CrowdTagsDetail;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ICrowdTagsDetailDao {

    void  addCrowdTagsUserId(CrowdTagsDetail crowdTagsDetailReq);

}


