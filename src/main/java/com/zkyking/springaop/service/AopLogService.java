package com.zkyking.springaop.service;

import java.util.List;

import com.zkyking.springaop.db.model.AopLog;

/**
 * Created with IntelliJ IDEA.
 * Author: zky
 * Date: 2019-12-15
 * Time: 18:52:14
 * Description:
 */
public interface AopLogService
{


    int updateBatch(List<AopLog> list);

    int updateBatchSelective(List<AopLog> list);

    int batchInsert(List<AopLog> list);

    int insertOrUpdate(AopLog record);

    int insertOrUpdateSelective(AopLog record);

}
