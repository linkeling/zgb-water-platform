package com.zgb.water.entity;
import java.util.Date;

import lombok.*;

/**
 * 档案管理
 * 
 * @author wsx
 * @version 2022-06-30
 */
@Getter
@Setter
public class ArchivesManage{
    /** 主键id */
    private Integer ruleFileId;
    /** 水库代码 */
    private String rscd;
    /** 目录主键id */
    private Integer dirId;
    /** 文件名称 */
    private String ruleFileName;
    /** 文件类型 */
    private String ruleType;
    /** 上传人 */
    private String updateUser;
    /** 附件 */
    private String fileUrl;
    /** 是否删除 0未删除 1已删除 */
    private Integer delFlag;
    /** 创建时间 */
    private Date createTime;
}