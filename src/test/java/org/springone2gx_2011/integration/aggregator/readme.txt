This demo shows one of the new features of the aggregator 

If you look at the aggregator configuration you can see that this aggregator will be releasing messages in groups of 5. 
But based on the notion of the aggregator a released group is a complete group which means that after the first 5 messages 
within a particular group were released the group will be complete and new group will be created once another message of the same group arrives.

"Late arrival" 
Some times you need to discard messages that arrive after a group is complete. In other words you need to tell aggregator 
that the message might belong to a group that is already closed (complete) and is no longer accepting messages (late messages). 
By defining 'expire-groups-upon-completion' and setting its value to 'false' allow you to achieve such functionality. 
Change it to 'true' and see ho different the output will be. You will see that every time the aggregator accumulates the groups 
of 5 it is releasing it (unlike the previous case)

"Leftover messages that don't quite complete the Release strategy requirement"
In our example we are releasing messages in groups of 5, but based on our code where we send 13 messages we'll have 3 
Messages leftover and no more coming. How do we release those messages? For that we configure MessageGroupStoreReaper 
which is a scheduled task that runs periodically and checks if a particular group must be expired (timeout property) and 
based on the setting in the aggregator (send-partial-result-on-expiry="true") partial results will be sent to the output channel