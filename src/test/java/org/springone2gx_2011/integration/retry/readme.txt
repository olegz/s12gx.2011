In this demo we demonstrate how easy it is to configure a 'retry' logic in Spring Integration.

This application contains two gateways
mainGateway - the entry point into the messaging flow as a whole
retryGateway - the entry point into the part of the flow that needs to be retried on the case of failure.

The simple use case is clearly visible in the 'upperCaserService' which simply upper cases a String value but 
only if such value is more than 4 characters long. If its not the exception will be thrown.

Run the application as is and you will see the exception "java.lang.IllegalArgumentException: Value is less than 4 characters"

Now let's add the error-channel="retryChannel"

In this case the will be propagated to the 'retryChannel' in the form of the ErrorMessage. You can see that 
'retryChannel' is just another channel with error handling components subscribed to it. First you see a delayer 
and than a transformer which attempts to fix the failed Message by padding its String payload value with ' ' and 
than it simply sending it back to the 'inputChannel' essentially resubmitting the same flow again.

Run the application again.

Although you still see the same exception in the logs you also see that the error is being handled and the flow 
is re-submitted with a new value. After 5 iterations you'll that the flow finally ends in success with the console 
showing you the following.

Reply: A  

The transformer used is simple SpEL-based transformer. You also see another version of the transformer (commented). 
It is the same transformer with the only difference. It does not use SpEL but actual Java class. You would use this 
approach if your transformation logic is more complex.