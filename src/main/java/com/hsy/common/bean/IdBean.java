package com.hsy.common.bean;

import java.io.Serializable;

import lombok.Data;

/**
 * @author 张梓枫
 * @date 2019年5月22日下午1:56:40
 * @description 对应数据库主键信息
 */
@Data
public class IdBean<PK> implements Serializable {

	private static final long serialVersionUID = 4439458877469360830L;

	private PK id;
}
