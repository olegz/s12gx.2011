In this demo we demonstrate an unbounded resequencer (no sequenceSize) with a unique requirement.

Message groups that belong to the same correlation ID must be processed serially, however Message groups that 
belong to different correlation ID could process concurrently.

The trick is in having a DirectChannel as an output-channel of the Resequencer and than have service-activator 
implementation that uses single permit java.util.concurrent.Semaphore which guards the lock for each 
correlation ID while delegating tasks execution to task-executor