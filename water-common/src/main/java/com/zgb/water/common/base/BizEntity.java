package com.zgb.water.common.base;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 项目业务实体类
 *
 * @author wsx
 * @version 2019-04
 */
@Getter
@Setter
public class BizEntity extends LongIdEntity {
    /**
     * 创建时间
     */
    private Date created;
    /**
     * 最后修改时间
     */
    private Date updated;
}
