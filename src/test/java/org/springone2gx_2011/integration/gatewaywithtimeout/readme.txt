This demo shows gateway could be additionally configured when reply is optional.

In our example we have a simple gateway interface that looks like this:

public interface EchoGateway {

	public String echo(String value);
}

As you can see the method has a non-void return signature which means such gateway will be 
waiting for a reply. But reply is something that might never be guaranteed in Messaging architectures 
and the example reproduces such scenario. You can see a simple router which routes all 'foo' messages 
to a service-activator that returns a reply while 'bar' messages are simply going to a logger never 
producing a reply. Such gateway call will block indefinitely. . .

Invoke that code and you'll see the logger output while the call to 'gateway.echo("bar")' would block.

What can you do?

Enable 'default-reply-timeout' (default-reply-timeout="3000") on the gateway, thus guaranteeing that
reply will always come in some form (e.g., null)