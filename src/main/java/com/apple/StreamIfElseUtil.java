/**   
 * Copyright © 2019 北京易酒批电子商务有限公司. All rights reserved.
 */
package com.apple;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Title: StreamIfElseUtil.java
 * @Package com.apple
 * @Description:
 * @author wangran
 * @date 2019年3月4日 下午2:40:20
 * @version V1.0
 */
public class StreamIfElseUtil<T> {

	private Stream<T> stream;

	private List<Predicate<T>> predicates = new ArrayList<>();

	private Integer count = 0;

	public StreamIfElseUtil(Stream<T> stream) {
		this.stream = stream;
	}

	public StreamIfElseUtil(Stream<T> stream, List<Predicate<T>> predicates, Integer count) {
		this.stream = stream;
		this.predicates = predicates;
		this.count = count;
	}

	public StreamIfElseUtil<T> select(Predicate<T> predicate) {
		this.predicates.add(predicate);
		return this;
	}

	public StreamIfElseUtil<T> with(Consumer<T> consumer) throws Exception {
		this.count++;
		final int port = this.count;
		if (this.predicates.isEmpty() || this.predicates.size() > this.count)
			throw new Exception("必须先调用select方法!");
		Stream<T> stream = this.stream.peek(t -> {
			if (this.predicates.get(port - 1).test(t)) {
				consumer.accept(t);
			}
		});
		return new StreamIfElseUtil<>(stream, this.predicates, this.count);
	}

	public StreamIfElseUtil<T> elseWith(Consumer<T> consumer) throws Exception {
		if (this.predicates.isEmpty())
			throw new Exception("必须先调用select方法!");
		Stream<T> stream = this.stream.peek(t -> {
			if (elseTest(t)) {
				consumer.accept(t);
			}
		});
		return new StreamIfElseUtil<>(stream);
	}

	private boolean elseTest(T t) {
		return this.predicates.stream().allMatch(predicate -> !predicate.test(t));
	}

	public Stream<T> getStream() {
		return stream;
	}
	
	public StreamIfElseUtil<T> accept(GenericServie<T> service) {
		Stream<T> stream = this.stream.peek(t -> service.accept(t));
		return new StreamIfElseUtil<>(stream);
	}

	public static void main(String[] args) throws Exception {
		List<Orders> orderList = new ArrayList<Orders>();
		Orders order1 = new Orders(1);
		Orders order2 = new Orders(15);
		Orders order3 = new Orders(25);
		
		orderList.add(order1);
		orderList.add(order2);
		orderList.add(order3);
		
//		BigDecimal result = new StreamIfElseUtil<Orders>(orderList.stream()).select(m -> m.getLevel() < 10)
//				.with(e -> e.setAmount(BigDecimal.valueOf(100)))
//				.select(m -> m.getLevel() >= 10 && m.getLevel() < 20)
//				.with(e -> e.setAmount(BigDecimal.valueOf(300)))
//				.elseWith(e -> e.setAmount(BigDecimal.valueOf(400)))
//				.getStream().map(m -> m.getAmount()).reduce(BigDecimal :: add)
//				.orElseGet(() -> BigDecimal.ZERO);
//
//		System.err.println(result);
		
		Integer result = orderList.stream().peek(e -> System.err.println("===" + e.getLevel())).map(m -> m.getLevel()).collect(Collectors.reducing(Integer :: sum)).get();
		System.err.println(result);
		Integer result2 = new StreamIfElseUtil<Orders>(orderList.stream()).accept(m -> System.err.println(m.getLevel())).getStream().map(m -> m.getLevel()).collect(Collectors.reducing(Integer :: sum)).get();
		System.err.println(result2);
	}

}
