This demo demonstrates one of the newest (since 2.1.M3) features within Spring Integration - Content Enricherwhich is exposed via <enricher> element

<enricher> is very similar to a <header-enricher>, however it has one big difference. Enrichment process itself could 
be a Messaging flow that is why <enricher> defines a 'request-channel' attribute which allows you to wire a Message flow 
which will be executed to get an additional data needed for the enrichemnt. In our use case we have a Company object that is 
missing a market data and you can see that we are actually getting a real market data based on sending an HTTP request to a 
Google service. Once market data is obtained it is injected into an enriched object.