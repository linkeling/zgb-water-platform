package com.zgb.water.service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zgb.water.common.base.Pagination;
import com.zgb.water.entity.ArchivesManage;
import com.zgb.water.param.output.ArchivesManageDTO;
import com.zgb.water.param.query.ArchivesManageQueryVO;

/**
 * 档案管理 Service
 * 
 * @author wsx
 * @version 2022-06-30
 */
public interface ArchivesManageService extends IService<ArchivesManage> {

    /**
     * 主键查询
     * @param id
     * @return
     */
    ArchivesManageDTO queryById(String id);


    /**
     * 分页查询
     * @param queryVO
     * @return
     */
    Pagination<ArchivesManageDTO> queryPagination(ArchivesManageQueryVO queryVO);
}