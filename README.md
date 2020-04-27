Xanadu is a next generation storage platform technology built for providing a highly scalable, available, consistent and fault tolerant NoSQL data store in a world of Big Data and extreme data resilience. 
Xanadu provides a unique combination of following features.
1. Unlimited Resilient Data Storage Capacity
2. Timeline Data Model 
3. Globally Atomic, Consistent, Isolated and Durable (ACID)

Xanadu implementation is currently split into four parts:
1.	XanaduRFT: The Xanadu implementation that uses the RAFT  distributed consensus protocol for resilient, consistent Key-Value updates in the face of node failure. 
2.	XanaduPX: An updated version, based on XanaduRFT, which uses the PAXOS  distributed consensus protocol instead for Key-Value updates.
3.	XanaduNext: A new implementation of the Xanadu codebase that is refactored to take the best aspects from the above two versions and anticipate the full range of capabilities planned and patented    for the platform.
4.	XanaduRFT (Community): XanaduRFT (Community) is essentially the same as XanaduRFT except it has some limitations to encourage users to use the full XanaduRFT or XanaduPX when moving from prototype applications into production.





