package com.zkyking.springaop.service.impl;

import com.zkyking.springaop.db.mapper.AopLogMapper;
import com.zkyking.springaop.db.model.AopLog;
import com.zkyking.springaop.service.AopLogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * Author: zky
 * Date: 2019-12-15
 * Time: 18:52:14
 * Description:
 */
@Service
public class AopLogServiceImpl implements AopLogService
{

    @Resource
    private AopLogMapper aopLogMapper;

    @Override
    public int updateBatch(List<AopLog> list)
    {
        return aopLogMapper.updateBatch(list);
    }

    @Override
    public int updateBatchSelective(List<AopLog> list)
    {
        return aopLogMapper.updateBatchSelective(list);
    }

    @Override
    public int batchInsert(List<AopLog> list)
    {
        return aopLogMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(AopLog record)
    {
        return aopLogMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(AopLog record)
    {
        return aopLogMapper.insertOrUpdateSelective(record);
    }

}
