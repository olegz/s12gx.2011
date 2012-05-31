package org.springone.timebasedaggregator;

import java.util.Random;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.integration.MessageChannel;
import org.springframework.integration.support.MessageBuilder;
/**
 * 
 * @author Oleg Zhurakousky
 *
 */
public class TimeBasedAggregationDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("aggregator-config.xml", TimeBasedAggregationDemo.class);
		
		MessageChannel inChannel = context.getBean("inChannel", MessageChannel.class);
		Random random = new Random();
		while (true) {
			inChannel.send(MessageBuilder.withPayload("VMW").setHeader("price", 95 + random.nextDouble()).build());
			Thread.sleep(random.nextInt(687));
		}
	}

}
