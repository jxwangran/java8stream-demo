/**   
 * Copyright © 2019 北京易酒批电子商务有限公司. All rights reserved.
 */
package com.apple;

/**    
* @Title: GenericServie.java  
* @Package com.apple  
* @Description: 
* @author wangran 
* @date 2019年3月4日 下午4:43:22  
* @version V1.0    
*/
@FunctionalInterface
public interface GenericServie<T> {

	void accept(T t);
	
}
