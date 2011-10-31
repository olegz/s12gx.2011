This demo consists of two parts

1. (error-config.xml) Some time when error occurs you want to use Messaging and Spring Integration 
to handle the error flow. The first configuration does just that by providing subscribers to the error-channel 
identified by the gateway. If such subscriber DOES NOT result in error, the overall request to this gateway will 
appear as a success. In this case you will see the request with payload of 'hello' is a success, but the one with 'bye' 
results in error. However you can see both of them reaching the logger, although the logger will print 'bye' message slightly 
altered by a transformer subscribing to the error-channel of the gateway.

LoggingHandler: [Payload=hello][Headers={...}]
LoggingHandler: [Payload=##bye##][Headers={. . .}]

2. (error-aggregator-config.xml) Why #1 is so important? Because you may actually have an aggregator that waits for a 
predetermined amount of messages and therefore even failed messages must be sent to the aggregator to satisfy its contract 
and that is exactly what #2 part is showing. So bootstrap this demo with the mentioned file and see the difference:
LoggingHandler: [Payload=[hello, ##bye##]][Headers={. . .}]

