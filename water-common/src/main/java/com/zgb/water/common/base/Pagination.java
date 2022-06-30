package com.zgb.water.common.base;

import com.fasterxml.jackson.annotation.JsonView;
import com.zgb.water.common.exception.BusinessException;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * 分页类
 * @author wsx
 * @version 2022-06-30
 */
@ApiModel
public class Pagination<D> implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "当前页的数据集合")
    @JsonView(BaseView.DataModel.class)
    private List<D> dataList;
    @ApiModelProperty(value = "总页数")
    @JsonView(BaseView.DataModel.class)
    private int pages;
    @ApiModelProperty(value = "总记录数")
    @JsonView(BaseView.DataModel.class)
    private int total;
    @ApiModelProperty(value = "每页记录数")
    @JsonView(BaseView.DataModel.class)
    private int pageSize;

    public Pagination(){}
    
    public Pagination(List<D> dataList, long total, int pageSize) {
        if(total > Integer.MAX_VALUE){
            throw new BusinessException("数据量过大");
        }
        this.dataList = dataList;
        this.total = (int)total;
        this.pageSize = pageSize == 0 ? 10 : pageSize;
        this.pages = (int)(total % this.pageSize == 0 ? total / this.pageSize : total / this.pageSize + 1);
    }
    
    public List<D> getDataList() {
        return dataList;
    }
    
    public void setDataList(List<D> dataList) {
        this.dataList = dataList;
    }

    public int getPages() {
        return pages;
    }
    
    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
    
    public int getPageSize() {
        return pageSize;
    }
    
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}