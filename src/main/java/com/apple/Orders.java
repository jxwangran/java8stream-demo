/**   
 * Copyright © 2019 北京易酒批电子商务有限公司. All rights reserved.
 */
package com.apple;

import java.math.BigDecimal;

/**
 * @Title: Orders.java
 * @Package com.apple
 * @Description:
 * @author wangran
 * @date 2019年3月4日 下午4:18:51
 * @version V1.0
 */
public class Orders {

	private BigDecimal amount;

	private Integer level;

	/**
	 * 
	 */
	public Orders() {
		super();
	}

	/**
	 * @param level
	 */
	public Orders(Integer level) {
		super();
		this.level = level;
	}

	/**
	 * @return the amount
	 */
	public BigDecimal getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	/**
	 * @return the level
	 */
	public Integer getLevel() {
		return level;
	}

	/**
	 * @param level the level to set
	 */
	public void setLevel(Integer level) {
		this.level = level;
	}

}
