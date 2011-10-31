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
package org.springone2gx_2011.integration.asyncgateway;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
/**
 * @author Oleg Zhurakousky
 * SpringOne2GX, Chicago 2011
 */
public class AsyncGatewayDemo {
	private static ExecutorService executor = Executors.newCachedThreadPool();
	private static int timeout = 10;
	
	@Test
	public void testAsyncGateway() throws Exception{
		ApplicationContext ac = new ClassPathXmlApplicationContext("async-gateway-config.xml", AsyncGatewayDemo.class);
		MyGateway gateway = ac.getBean("mathService", MyGateway.class);
		
		List<Future<String>> results = new ArrayList<Future<String>>();
		for (int i = 0; i < 100; i++) {
			Future<String> result = gateway.process("Hello Spring One");
			results.add(result);
		}
		
		for (final Future<String> result : results) {
			executor.execute(new Runnable() {		
				public void run() {
					try {
						String finalResult =  result.get(timeout, TimeUnit.SECONDS);
						System.out.println("Final Result is: " + finalResult);
					} catch (ExecutionException e) {
						System.out.println("Exception happened during the execution of the gateway method: " + e);
					} catch (TimeoutException tex){
						System.out.println("Timeout occured while waiting for the execution of the gateway method");
					} catch (Exception ie){
						System.out.println(ie);
						Thread.currentThread().interrupt();
					}
				}
			});
		}
		executor.shutdown();
		executor.awaitTermination(60, TimeUnit.SECONDS);
	}
}
