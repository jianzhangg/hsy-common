/**
 * 
 */
package com.hsy.common.bean;

import java.io.Serializable;

import com.hsy.common.utils.ObjectUtils;

import lombok.Data;

/**
 * @author 张梓枫
 * @Description 分页参数类
 * @date:   2019年1月3日 上午9:46:00
 */
@Data
public class PageInfo implements Serializable{

    private static final long serialVersionUID = 524366622970361020L;

	private static final Integer PAGE_NUM_ZERO = 0;

    private Integer pageNum;
    
    private Integer pageSize;

    private String orderBy;
    
    public PageInfo(Integer pageNum, Integer pageSize) {
        this(pageNum,pageSize,null);
    }
    
    public PageInfo(Integer pageNum, Integer pageSize, String orderBy) {
		if (ObjectUtils.equals(pageNum, PAGE_NUM_ZERO)) {
            pageNum = 1;
        }
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.orderBy = orderBy;
    }
}
