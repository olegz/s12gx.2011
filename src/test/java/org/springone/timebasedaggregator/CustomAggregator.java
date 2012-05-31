package org.springone.timebasedaggregator;

import java.util.Date;
import java.util.List;

import org.springframework.integration.Message;
import org.springframework.integration.support.MessageBuilder;
/**
 * Will calculate the average price including in in the final (aggregated) message 
 * 
 * @author Oleg Zhurakousky
 *
 */
public class CustomAggregator {

	public Message<?> aggregate(List<Message<?>> messages){
		
		int messagesSize = messages.size();
		if (messagesSize == 10){
			System.out.println("--> Releasing based on accumulation of 10 messages at: " + new Date(System.currentTimeMillis()));
		}
		String ticker = null;
		double sum = 0;
		for (Message<?> message : messages) {
			if (!(message.getPayload() instanceof Message)){ // discarding a Marker Message
				if (ticker == null) {
					ticker = (String) message.getPayload();
				}
				double currentPrice = (Double) message.getHeaders().get("price");
				sum += currentPrice;
			}
		}
		
		double averagePrice = sum/messagesSize;
		Message<String> aggregatedMessage = MessageBuilder.withPayload(ticker).setHeader("averagePrice", averagePrice).build();
		return aggregatedMessage;
	}
}
