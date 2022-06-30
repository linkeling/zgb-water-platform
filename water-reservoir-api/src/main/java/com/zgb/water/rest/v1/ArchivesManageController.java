package com.zgb.water.rest.v1;
import com.zgb.water.common.base.Pagination;
import com.zgb.water.common.base.ResponseDataModel;
import com.zgb.water.param.output.ArchivesManageDTO;
import com.zgb.water.param.query.ArchivesManageQueryVO;
import com.zgb.water.service.ArchivesManageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 功能：档案管理 接口
 * @author wsx
 * @version 2022-06-30
 */
@Api(tags = "档案管理")
@RestController
@RequestMapping("/v1/water-reservoir-api/archives-manage")
public class ArchivesManageController {
    @Autowired
    private ArchivesManageService manageService;

    @ApiOperation(value = "1.01 主键查询", httpMethod = "GET", notes = "1.01 根据id查询数据")
    @ApiImplicitParam(dataType = "string", name="id",value = "主键",example = "1",type="path",dataTypeClass = String.class)
    @GetMapping("/{id}")
    public ResponseDataModel<ArchivesManageDTO> queryById(@PathVariable String id) {
        return ResponseDataModel.ok((ArchivesManageDTO)manageService.queryById(id));
    }

    /**
     * 功能:分页查询
     *
     * @param vo        查询条件
     */
    @ApiOperation(value = "1.02 分页查询", httpMethod = "GET", notes = "1.02 根据参数获取列表")
    @GetMapping
    public ResponseDataModel<Pagination<ArchivesManageDTO>> queryPagination(@Validated ArchivesManageQueryVO vo) {
        return ResponseDataModel.ok(manageService.queryPagination(vo));
    }
}