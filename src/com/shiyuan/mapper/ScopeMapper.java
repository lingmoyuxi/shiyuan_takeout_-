package com.shiyuan.mapper;

import com.shiyuan.model.Scope;
import com.shiyuan.model.ScopeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ScopeMapper {
    long countByExample(ScopeExample example);

    int deleteByExample(ScopeExample example);

    int deleteByPrimaryKey(Long scopeId);

    int insert(Scope record);

    int insertSelective(Scope record);

    List<Scope> selectByExample(ScopeExample example);

    Scope selectByPrimaryKey(Long scopeId);

    int updateByExampleSelective(@Param("record") Scope record, @Param("example") ScopeExample example);

    int updateByExample(@Param("record") Scope record, @Param("example") ScopeExample example);

    int updateByPrimaryKeySelective(Scope record);

    int updateByPrimaryKey(Scope record);
}