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
package org.springone2gx_2011.integration.enricher;

import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.integration.Message;
import org.springframework.integration.MessageChannel;
import org.springframework.integration.support.MessageBuilder;

/**
 * @author Oleg Zhurakousky
 * SpringOne2GX, Chicago 2011
 */
public class EnricherDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		
		ApplicationContext context = new ClassPathXmlApplicationContext("enricher-config.xml", EnricherDemo.class);
		Company original = new Company();
		original.setName("VMWare");
		original.setTicker("VMW");
		System.out.println("Sending company: " + original);
		Message<?> request = MessageBuilder.withPayload(original).build();
		context.getBean("inputChannel", MessageChannel.class).send(request);
	}
	
	/**
	 *
	 */
	public static class Company {
		
		private volatile String name;
		
		private volatile String ticker;
		
		private volatile long updated;
		
		private volatile Map<String, String> marketInformation;
		
		private volatile String updatePlace;
		
		public String getUpdatePlace() {
			return updatePlace;
		}

		public void setUpdatePlace(String updatePlace) {
			this.updatePlace = updatePlace;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getTicker() {
			return ticker;
		}

		public void setTicker(String ticker) {
			this.ticker = ticker;
		}

		public long getUpdated() {
			return updated;
		}

		public void setUpdated(long updated) {
			this.updated = updated;
		}

		public Map<String, String> getMarketInformation() {
			return marketInformation;
		}

		public void setMarketInformation(List<Map<String, String>> marketInformation) {
			this.marketInformation = marketInformation.get(0);
		}		
		
		public String toString(){
			return name + "; " + ticker + "; " + marketInformation + "; " + updated + "; " + updatePlace;
		}
	}
}
