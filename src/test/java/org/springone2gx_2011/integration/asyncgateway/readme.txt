This demo demonstrates a very simple but powerfull concept of Asynchronous gateway

Async gateway is simply realized via method signature with return value of java.util.concurrent.Future<?>.

Such gateway method call will always return immediately without blocking and you can interogate the Future itself to get a response when it comes.