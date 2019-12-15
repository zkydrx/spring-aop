package com.zkyking.springaop.db.mapper;

import com.zkyking.springaop.db.model.AopLog;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Author: zky
 * Date: 2019-12-15
 * Time: 18:52:14
 * Description:
 */
public interface AopLogMapper extends Mapper<AopLog>
{
    int updateBatch(List<AopLog> list);

    int updateBatchSelective(List<AopLog> list);

    int batchInsert(@Param("list") List<AopLog> list);

    int insertOrUpdate(AopLog record);

    int insertOrUpdateSelective(AopLog record);
}