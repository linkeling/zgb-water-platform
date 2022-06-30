package com.zgb.water.param.query;
import com.zgb.water.common.base.PaginationVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 查询档案管理VO
 * 
 * @author wsx
 * @version 2022-06-30
 */
@Data
@EqualsAndHashCode(callSuper=true)
@ApiModel(description = "查询档案管理VO")
public class ArchivesManageQueryVO extends PaginationVO {

    /** 主键id */
    @ApiModelProperty(value = "主键id")
    private Integer ruleFileId;

    /** 水库代码 */
    @ApiModelProperty(value = "水库代码", position = 1)
    private String rscd;

    /** 目录主键id */
    @ApiModelProperty(value = "目录主键id", position = 2)
    private Integer dirId;

    /** 文件名称 */
    @ApiModelProperty(value = "文件名称", position = 3)
    private String ruleFileName;

    /** 文件类型 */
    @ApiModelProperty(value = "文件类型", position = 4)
    private String ruleType;

    /** 上传人 */
    @ApiModelProperty(value = "上传人", position = 5)
    private String updateUser;

    /** 附件 */
    @ApiModelProperty(value = "附件", position = 6)
    private String fileUrl;

    /** 是否删除 0未删除 1已删除 */
    @ApiModelProperty(value = "是否删除 0未删除 1已删除", position = 7)
    private Integer delFlag;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 8)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
}