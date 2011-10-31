In this demo we demonstrate how you can provide your own strategy to generate Message ID

Currently Spring Integration is using UUID.randomUUID(). Although globally unique it is not very performant.

You can provide your own UUID generator via IdGenerator strategy

You can also fine a simple (version 1 UUID) class which you can incorporate in your implementation of 
IdGenerator here: https://github.com/olegz/uuid/blob/master/uuidgen/src/main/java/org/olegz/uuid/TimeBasedUUIDGenerator.java. 
Simple performance test will show you that it is on average 10 times faster.