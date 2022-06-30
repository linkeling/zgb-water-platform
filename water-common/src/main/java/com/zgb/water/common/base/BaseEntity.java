
package com.zgb.water.common.base;

import java.io.Serializable;

/**
 * 基础实体对象
 *
 * @author wsx
 * @version 2022-06-30
 */
public abstract class BaseEntity implements Serializable {
    private static final long serialVersionUID = -2403825263026437472L;
//    private Long creatorId;
//    private Date createTime;
//    private Long operId;
//    private Date operTime;
//    private Boolean validity;

    @Override
    public abstract boolean equals(Object obj);

    @Override
    public abstract int hashCode();

//    public Long getCreatorId() {
//        return creatorId;
//    }
//
//    public void setCreatorId(Long creatorId) {
//        this.creatorId = creatorId;
//    }
//
//    public Date getCreateTime() {
//        return createTime;
//    }
//
//    public void setCreateTime(Date createTime) {
//        this.createTime = createTime;
//    }
//
//    public Long getOperId() {
//        return operId;
//    }
//
//    public void setOperId(Long operId) {
//        this.operId = operId;
//    }
//
//    public Date getOperTime() {
//        return operTime;
//    }
//
//    public void setOperTime(Date operTime) {
//        this.operTime = operTime;
//    }
//
//    public Boolean getValidity() {
//        return validity;
//    }
//
//    public void setValidity(Boolean validity) {
//        this.validity = validity;
//    }
}