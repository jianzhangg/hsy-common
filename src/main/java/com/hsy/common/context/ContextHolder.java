/**
 * 
 */
package com.hsy.common.context;

import com.hsy.common.bean.PageInfo;
import com.hsy.common.bean.User;

/**
 * @author 张梓枫
 * @date 2019年4月28日上午10:13:34
 * @desc 存储jwt的用户登录信息
 */
public class ContextHolder {

	private static final ThreadLocal<User> userLocal = new ThreadLocal<User>();
    
    private static final ThreadLocal<PageInfo> pageLocal = new ThreadLocal<>();

	public static void setUser(User user) {
        userLocal.set(user);
    }

	public static User getUser() {
        return userLocal.get();
    }
    
	public static void setPage(PageInfo pageInfo) {
        pageLocal.set(pageInfo);
    }

    public static PageInfo getPage() {
        return pageLocal.get();
    }
    
    public static void clear() {
        userLocal.remove();
        pageLocal.remove();
    }
}
