In this demo we demonstrate how easy it is to segment (scope) flows to represent each individual segment as an independent subflow thus allowing transactions and error handling to be handled within each segment

Segmenting the flow is very simple. All you need is have a service-activator that references the gateway:

        <int:service-activator input-channel="segmentThreeChannel" ref="segmentThree"/>
	
	<int:gateway id="segmentThree" default-request-channel="segmentThreeInputChannel" error-channel="s3Error"/>

Since by definition each gateway represents an entry point to a Messaging system, each gateway represents an entry 
point to a flow segment and all errors and transactions will be scoped within that segment.

If you look at the example we are sending numbers to the flow and based on the number you send an exception might happen 
and will be handled within the segment where it happened.

For example if you invoke 'gateway.process(10)' you'll see a successful invocation:

  Result:15

Invoke it with value '11' and you'll see 'java.lang.ArithmeticException: / by zero' and since we handle error you 
will still see a result output:

  Result:#11# (single hash character signifies that exception happened in the first segment)

Invoke it with value '15' and you'll see 'java.lang.ArithmeticException: / by zero' and since we handle error you 
will still see a result output:

  Result:##15## (double hash characters signify that exception happened in the second segment)

Invoke it with value '20' and you'll see 'java.lang.ArithmeticException: / by zero' and since we handle error you 
will still see a result output:

  Result:###22### (tripple hash characters signify that exception happened in the third segment)