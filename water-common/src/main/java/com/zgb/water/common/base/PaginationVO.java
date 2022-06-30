
package com.zgb.water.common.base;

import com.zgb.water.common.Constants;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

/**
 * 分页查询参数VO基类
 *
 * @author wsx
 * @version 2018-11
 */
@ApiModel
public class PaginationVO {
    @ApiModelProperty(value = "当页页数[1,100000]", example = "1", required = true, allowableValues = "range[1,100000]")
    @Range(min = 1, max = 100000)
    @NotNull(message = "当天页数不能为空")
    private int pageIndex;

    @ApiModelProperty(value = "每页记录数[1,10000]", example = "10", required = true, allowableValues = "range[1,10000]")
    @Range(min = 1, max = 10000)
    @NotNull(message = "每页记录数不能为空")
    private int pageSize;

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex <= 0 ? 1 : pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toStringExclude(this, Constants.excludeFieldNames);
    }
}
