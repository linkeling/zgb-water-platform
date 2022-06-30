package com.zgb.water.service.impl;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zgb.water.common.base.Pagination;
import com.zgb.water.dao.ArchivesManageDAO;
import com.zgb.water.entity.ArchivesManage;
import com.zgb.water.param.output.ArchivesManageDTO;
import com.zgb.water.param.query.ArchivesManageQueryVO;
import com.zgb.water.service.ArchivesManageService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * 档案管理 Service 实现类
 * 
 * @author wsx
 * @version 2022-06-30
 */
@Service
public class ArchivesManageServiceImpl extends ServiceImpl<ArchivesManageDAO,ArchivesManage> implements ArchivesManageService {

    @Override
    public ArchivesManageDTO queryById(String id) {
        ArchivesManage entity=baseMapper.selectById(id);
        ArchivesManageDTO dto = new ArchivesManageDTO();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }

    @Override
    public Pagination<ArchivesManageDTO> queryPagination(ArchivesManageQueryVO queryVO) {
        Page page = new Page(queryVO.getPageIndex(), queryVO.getPageSize());
        IPage<ArchivesManageDTO> iPage = baseMapper.getArchivesManageByPage(page, queryVO);
        return new Pagination<>(iPage.getRecords(), page.getTotal(), queryVO.getPageIndex());
    }
}