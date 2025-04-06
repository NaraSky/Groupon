package com.lb.infrastructure.dao;

import com.lb.infrastructure.dao.po.NotifyTask;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface INotifyTaskDao {

    void insert(NotifyTask notifyTask);

}
