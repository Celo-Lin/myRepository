package com.juan.adx.common.model;

import com.github.pagehelper.PageInfo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageData implements Serializable {

    private static final long serialVersionUID = 284823113000309624L;

    /**
     * 分页数，从1开始
     */
    private Integer pageNo;

    /**
     * 每页条数
     */
    private Integer pageSize;

    /**
     * 查询出来的总条数
     */
    private long totalCount;

    /**
     * 数据
     */
    private Object pageData;
    

    public PageData addPageInfo(PageInfo<?> pageInfo, Object pageData) {
        this.pageNo = pageInfo.getPageNum();
        this.pageSize = pageInfo.getPageSize();
        this.totalCount = pageInfo.getTotal();
        this.pageData = pageData;
        return this;
    }
}