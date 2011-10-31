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
package org.springone2gx_2011.integration.error;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Oleg Zhurakousky
 * SpringOne2GX, Chicago 2011
 */
public class ErrorDemo {

	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("error-config.xml", ErrorDemo.class);
//		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("error-aggregator-config.xml", ErrorDemo.class);
		ErrorDemoGateway gateway = context.getBean(ErrorDemoGateway.class);
		gateway.process(Arrays.asList("bye", "hello"));
	}
	
	public interface ErrorDemoGateway {
		public Object process(List<?> data);
	}

}
