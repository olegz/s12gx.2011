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
package org.springone2gx_2011.integration.retry;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.integration.Message;
import org.springframework.integration.annotation.Payload;

/**
 * @author Oleg Zhurakousky
 * SpringOne2GX, Chicago 2011
 */
public class RetryDemo {

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("retry-config.xml", RetryDemo.class);
		RetryGateway gateway = context.getBean(RetryGateway.class);
		String reply = gateway.process("a");
		System.out.println("Reply: " + reply);
	}
	
	
	public interface RetryGateway{
		public String process(String value);
	}
	
	public static class UpperCaser{
		public String toUpperCase(Message<?> value){
			System.out.println("Will attempt to upper case value '" + value.getPayload() + "'");
			if (((String) value.getPayload()).length() > 4){
				
				return ((String) value.getPayload()).toUpperCase();
			}
			else {
				System.out.println("Will retry");
				throw new IllegalArgumentException("Value is less than 4 characters");
			}
		}
	}
	
	public static class MyTransformer {
		public Object foo(@Payload(value="#this.getFailedMessage().getPayload()") String payload){
			return payload + " ";
		}
	}

}
