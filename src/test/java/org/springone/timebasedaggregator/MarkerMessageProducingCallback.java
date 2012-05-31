package org.springone.timebasedaggregator;

import java.util.Date;

import org.springframework.integration.Message;
import org.springframework.integration.store.MessageGroup;
import org.springframework.integration.store.MessageGroupCallback;
import org.springframework.integration.store.MessageGroupStore;
import org.springframework.integration.support.MessageBuilder;

/**
 * Once the MessageGroup is complete, the timeout calculation will not begin until the new MessageGroup is created.
 * This means that the actual time-based release will happen timeout + wait-time-for-the-first-message. That would be acceptable at the 
 * start of the application, however it its not once the application is running and we are trying to collect quotes for
 * the MessageGroup that we are dealing with already. 
 * So this callback class will send a Marker Message (the last Message in the just released group) back to the MessageGroup, thus
 * resetting the clock. However the Marker message will be ignored in the CustomAggregator when average price is calculated 
 * 
 * @author Oleg Zhurakousky
 *
 */
public class MarkerMessageProducingCallback implements MessageGroupCallback {

	public void execute(MessageGroupStore messageGroupStore, MessageGroup group) {
		System.out.println("--> Releasing Message Group based on time expiration at: " + new Date(System.currentTimeMillis()));
		Message<?> lastMessage = group.getMessages().iterator().next();
		Message<?> markerMessage = MessageBuilder.withPayload(lastMessage).build();
		messageGroupStore.addMessageToGroup(group.getGroupId(), markerMessage);
	}

}
