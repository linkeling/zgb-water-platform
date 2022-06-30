package com.zgb.water.dao;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zgb.water.entity.ArchivesManage;
import com.zgb.water.param.output.ArchivesManageDTO;
import com.zgb.water.param.query.ArchivesManageQueryVO;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * 档案管理 DAO
 * 
 * @author wsx
 * @version 2022-06-30
 */
public interface ArchivesManageDAO extends BaseMapper<ArchivesManage> {

    /**
     * 分页查询
     * @param page
     * @param vo
     * @return
     */
    IPage<ArchivesManageDTO> getArchivesManageByPage(Page page, ArchivesManageQueryVO vo);
}