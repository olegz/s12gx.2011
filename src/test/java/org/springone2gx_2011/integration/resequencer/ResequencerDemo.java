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
package org.springone2gx_2011.integration.resequencer;

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Semaphore;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.integration.MessageChannel;
import org.springframework.integration.MessageHeaders;
import org.springframework.integration.annotation.Header;
import org.springframework.integration.support.MessageBuilder;

/**
 * @author Oleg Zhurakousky
 * SpringOne2GX, Chicago 2011
 */
public class ResequencerDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		ApplicationContext context = new ClassPathXmlApplicationContext("resequencer-config.xml", ResequencerDemo.class);
		MessageChannel inputChannel = context.getBean("inputChannel", MessageChannel.class);
		
		inputChannel.send(MessageBuilder.withPayload(5).setCorrelationId(1).setSequenceNumber(5).build());
		inputChannel.send(MessageBuilder.withPayload(2).setCorrelationId(1).setSequenceNumber(2).build());
		inputChannel.send(MessageBuilder.withPayload(1).setCorrelationId(1).setSequenceNumber(1).build());
		
		inputChannel.send(MessageBuilder.withPayload(2).setCorrelationId(2).setSequenceNumber(2).build());
		inputChannel.send(MessageBuilder.withPayload(1).setCorrelationId(2).setSequenceNumber(1).build());
		
		inputChannel.send(MessageBuilder.withPayload(4).setCorrelationId(1).setSequenceNumber(4).build());
		inputChannel.send(MessageBuilder.withPayload(3).setCorrelationId(1).setSequenceNumber(3).build());
		
		inputChannel.send(MessageBuilder.withPayload(5).setCorrelationId(2).setSequenceNumber(5).build());	
		inputChannel.send(MessageBuilder.withPayload(4).setCorrelationId(2).setSequenceNumber(4).build());
		inputChannel.send(MessageBuilder.withPayload(3).setCorrelationId(2).setSequenceNumber(3).build());
		
		System.in.read();
	}
	
	public static class SequenceProcessor {
		
		private final ConcurrentHashMap<Object, Semaphore> locks = new ConcurrentHashMap<Object, Semaphore>();
		private final Random random = new Random();
		private final Executor taskExecutor;
		
		public SequenceProcessor(Executor taskExecutor){
			this.taskExecutor = taskExecutor;
		}

		public void process(final Integer payload, final @Header(MessageHeaders.CORRELATION_ID) Object correlationId) throws Exception{
			locks.putIfAbsent(correlationId, new Semaphore(1, true));
			this.taskExecutor.execute(new Runnable() {		
				public void run() {
					try {
						locks.get(correlationId).acquire();			
						System.out.println("Processing: " + payload + " correlationId: "+ correlationId);
						Thread.sleep(random.nextInt(2000));
						locks.get(correlationId).release();
					} catch (Exception e) {
						e.printStackTrace();
					}			
				}
			});
		}
	}
}
