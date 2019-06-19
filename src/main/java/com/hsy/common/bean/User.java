/**
 * 
 */
package com.hsy.common.bean;

import lombok.Getter;
import lombok.Setter;

/**
 * @author 张梓枫
 * @date 2019年4月28日上午10:11:17
 * @desc JWT封装用户信息，用户JWT用户认证
 */
@Getter
@Setter
public class User extends IdBean<Integer> {
    
    private static final long serialVersionUID = -8707493434063316909L;
    
	private Integer sellerId;

    private String username; 

}
