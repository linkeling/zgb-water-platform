package com.zgb.water.common.base;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * DAO接口基类
 *
 * @author wsx
 * @version 2022-06-30
 */
@SuppressWarnings("all")
public interface BaseDAO<E,C> {
    long countByCriteria(C criteria);

    int deleteByCriteria(C criteria);

    int deleteByPrimaryKey(Long id);

    int insert(E record);

    int insertSelective(E record);

    List<E> selectByCriteria(C criteria);

    E selectByPrimaryKey(Long id);

    int updateByCriteriaSelective(@Param("record") E record, @Param("criteria") C criteria);

    int updateByCriteria(@Param("record") E record, @Param("criteria") C criteria);

    int updateByPrimaryKeySelective(E record);

    int updateByPrimaryKey(E record);
}
