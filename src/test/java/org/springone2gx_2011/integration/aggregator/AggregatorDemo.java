/*
 * Copyright 2002-2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springone2gx_2011.integration.aggregator;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.integration.Message;
import org.springframework.integration.MessageChannel;
import org.springframework.integration.support.MessageBuilder;

/**
 * @author Oleg Zhurakousky
 * SpringOne2GX, Chicago 2011
 */
public class AggregatorDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		ApplicationContext context = new ClassPathXmlApplicationContext("aggregator-config.xml", AggregatorDemo.class);
		MessageChannel inputChannel = context.getBean("inputChannel", MessageChannel.class);
		int iterations = 13;
		for (int i = 0; i < iterations; i++) {
			Message<?> message = MessageBuilder.withPayload(i).setCorrelationId(1).
						setSequenceNumber(i).setSequenceSize(iterations).build();
			System.out.println("Sending: " + message);
			inputChannel.send(message);
			
			Thread.sleep(1000);
		}
	}

}
