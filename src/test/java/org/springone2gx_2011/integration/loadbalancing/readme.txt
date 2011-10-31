This demo demonstrates automatic Failover and Load Balancing capabilities of Spring Integration.

DirectChannel enforces P2P messaging model where a Message will be delivered to only one subscriber. 
However DirectChannel can have multiple subscribers which will act as either back-up subscribers for the failover 
cases and/or load distribution subscribers, all based on simple configuration.

As an example this application uses two subscribers. One is AMQP Outbound Gateway and the other one is JMS Outbound Gateway. 
There are two use cases wher such configuration would be important.

Load-balancing
==============

Let's say you have an application that needs to distribute the load across multiple remote systems. The ideal 
solution would be to have several endpoints (e.g., outbound gateway/adapters) subscribing to a P2P channel. By default 
Spring Integration message dispatcher will apply 'round-robin' load balancing policy where each message sent to a P2P 
channel will be distributed to the next subscriber in the list and once it reaches the end of the list the process repeats.

Run this application as is and you will see from the logs that 2 messages were sent over AMQP and one over JMS

LoggingHandler: [Payload=B][Headers={..., jms_redelivered=false, jms_messageId=ID:oleg-56506-1319729174439-2:0:2:1:1}]
LoggingHandler: [Payload=A][Headers={..., amqp_contentEncoding=UTF-8, amqp_deliveryTag=1, amqp_redelivered=false}]
LoggingHandler: [Payload=C][Headers={..., amqp_contentEncoding=UTF-8, amqp_deliveryTag=2, amqp_redelivered=false}]

If you don't want this behavior you can add <int:dispatcher load-balancer="none"/> to the 'inputChannel' and you'll 
see that all messages will go through AMQP since AMQP Outbound Gateway is a primary message subscriber since its 'order' 
attribute is smaller than the same attribute in JMS Outbound Gateway.

Try it and try to play wit the order attributes to change primary subscriber.


Failover
========
Let's say you have to communicate with remote system. You chose a primary protocol to be AMQP (e.g. you are using RabbitMQ), 
but such system could itself become unavailable during the runtime (e.g., server crash). This does not mean your application 
have to be unavailable, since your application can temporarily switch to a different protocol to communicate with remote 
system (e.g., JMS, HTTP etc.). And that is exactly what this demo does.

The primary outbound gateway is AMQP and the secondary is JMS.

Shut down AMQP and run the demo. You'll see that the demo completes successfully even though AMQP gateway will fail. 
In a cases where P2P channel has more then one subscriber Spring Integration will automatically failover to the next 
subscriber if the previous one failed until it reaches the end of the subscriber list. Only than the exception will be 
re-thrown back to the caller.
