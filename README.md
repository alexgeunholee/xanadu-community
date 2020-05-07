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


For an introductory overview of Xanadu, please see following video.

Link: https://www.youtube.com/watch?v=3GJcFCsQzCI&list=UUkERuP7J9mW1tY1dgN_PttQ&index=55


Followings show some demos of Xanadu performance

Xanadu Fault Tolerance Demo

Link: https://www.youtube.com/watch?v=H2LVMuHaJEo&feature=youtu.be

Xanadu Real Time Streaming Video Data Archiving for Hadoop Analysis Demo

Link: https://www.youtube.com/watch?v=u3rM_yuNxCE&list=UUkERuP7J9mW1tY1dgN_PttQ&index=69

Xanadu Big Data Platform Technology BMT@ Rackspace Cloud

Link: https://www.slideshare.net/alexglee/xanadu-big-data-platform-technology-bmt-rackspace-cloud


Followings illustrate some use cases of Xanadu

Xanadu Big Data DB + Blockchain Integration Demo

A demo to show blockchain-Xanadu database integration for medical big data store, access, and exploitation. 
This demo implements a GUI that visualizes the transaction in blockchain layer & data store/access in Xanadu distributed database using medical image data.

Link: https://www.youtube.com/watch?v=uhPaWcUSv10

Xanadu + Big Data Deep Learning Integration Demo

Xanadu provides a composable architecture that can be integrated with other big data systems: 
Xanadu can be integrated with Hadoop based Big Data Deep Leaning on Local Machines & AWS Clusters.
CNN/RNN architecture in DeepLearning4J (DL4J) on CPUs/GPUs:
Xanadu file system/Hodoop adapter is used to store and load data directly from DL4J Local Machines & AWS Clusters.

Link: https://www.youtube.com/watch?v=BJ16Q-8Zu8s&feature=youtu.be

Xanadu based CBIR System for Automated Medical Diagnosis Demo

Xanadu based CBIR (Content-Based Image Retrieval) system provides a scalable and efficient architecture that enables archiving of large number of records that allows quick retrieval of similar images.
Xanadu based Medical CBIR System can provide an automated diabetic retinopathy diagnosis using pre-processed retina images.

Link: https://www.youtube.com/watch?v=b1mxdTqb684&feature=youtu.be

Xanadu based Cloud Computing Use Case Demo

Xanadu can be exploited for the back-end storage technology that allows remote client computers can access data and computing/application resources via the standard iSCSI network protocol. With iSCSI supported natively by any operating system, Xanadu makes it easy to securely store and access data from any machine on the network. 
Xanadu can be used for providing services that allow remote computer systems to “boot” from a stored system drive image. 
With diskless units on users’ desks and all data (including the operating system disk) remains in the secure cloud servers, administrators are free to deploy diskless PCs to the desktop with their inherent advantages of higher data security, quicker disaster recovery, smaller office footprint and better energy consumption. 

Link: https://www.youtube.com/watch?v=FQcMrd6Jczw&feature=youtu.be

Xanadu + Seagate Kinetic/Ember System Video File Store/Retrieve Demo

Xanadu exploits the Distributed File System (DFS) technology using commodity servers and storage devices. 
Xanadu enables database with built-in massively parallel big data processing exploiting the DFS cost effectively. 

Link: https://www.youtube.com/watch?v=5gLB_qPu-TU&feature=youtu.be


Followings show patents for Xanadu

US10067719: Methods and systems for storing and accessing data in a distributed data storage system

Abstract: The present disclosure provides methods and systems for storing and accessing data in a distributed data storage system. The distributed data storage system includes a plurality of data storage nodes. The method includes receiving a data storing request. Then, a pseudo-random sequence of data storage nodes of the plurality of data storage nodes is generated, wherein the pseudo-random sequence is not sequential from any starting position and each data storage node of the pseudo-random sequence of data storage nodes is determined using a pre-determined pseudo-random function that is a function of a data reference. Finally, a data is stored into at least one data storage node of the pseudo-random sequence of data storage nodes based on a pre-defined selection criteria. The distributed data storage system includes an access server configured to store into and access data from at least one data storage node of data storage nodes.

Link: https://patents.google.com/patent/US10067719B1

US10649980: Methods and systems for resilient, durable, scalable, and consistent distributed timeline data store

Abstract: The present disclosure discloses methods and systems for managing data in a database in accordance with a data model, henceforth referred to as a “timeline store” or “timeline model”. The method includes mapping each data reference of one or more data references with a data value of a block of data in a data store. Then, each key reference of one or more key references is mapped with at least one pair of a time reference and the data reference in a timeline store, the time reference determines a point in time at which the key reference is assigned the pair of the time reference and the data reference. Thereafter, the data model is queried to retrieve a set of key references mapped to data references based on a specific ranges of keys and time or time intervals. Finally, the data model is queried to retrieve the raw data bytes associated/mapped with/to any desired data reference.

Link: https://patents.google.com/patent/US10649980B1

US10158483: Systems and methods for efficiently and securely storing data in a distributed data storage system

Abstract: The present disclosure discloses a method of storing data in a distributed data storage system, the distributed data storage system including a plurality of server and client nodes. The method includes receiving unencrypted data from a client node for storing at a server node. The received data is split into one or more data chunks of one or more sizes. Further, each data chunk is encrypted using a key based on the content of corresponding data chunk, and each encrypted chunk is stored at a memory of a server node using a unique data reference. Furthermore, an index chunk is formed that contains one or more data references of one or more encrypted chunks in a predefined order, along with one or more corresponding encryption keys of one or more encrypted chunks, which after being encrypted and stored, the corresponding data reference of this encrypted index chunk is provided to the client node.

Link: https://patents.google.com/patent/US10158483B1

US10275400: Systems and methods for forming a fault-tolerant federated distributed database

Abstract: The present disclosure provides a method for forming a fault-tolerant federated distributed database system, wherein the federated distributed database system includes a plurality of globally distributed local agreement groups, each globally distributed local agreement group including a plurality of computing nodes. The method includes storing data in each computing node in accordance with a temporal data model, a block corresponding to the temporal data model storing one or more keys, and time value and data reference value corresponding to each key, wherein the time value determines a point in time at which corresponding key is assigned corresponding data reference value. Then, for each globally distributed local agreement group, one or more blocks of corresponding computing nodes are updated, by updating state of corresponding one or more keys simultaneously using a local agreement algorithm. Further, for each globally distributed local agreement group, one or more updated blocks of the one or more corresponding computing nodes are combined to form a corresponding combined block of key edits. Furthermore, one or more key edits of combined blocks of the plurality of globally distributed local agreement groups are being agreed upon based on a pre-defined range of time and keys, using a global distributed agreement algorithm.

Link: https://patents.google.com/patent/US10275400B1

US10176202: Methods and systems for content-based image retrieval

Abstract: The present disclosure discloses a method for retrieval of similar images from a database. For a plurality of input images, each input image is disintegrated into a plurality of image patches of one or more predefined pixel sizes. A representation of each image patch is computed using a dimensional reduction method and distances between the representations of each image patch are compared. Further, the representations of each image patch into addressable hyper-cubes are quantized, wherein an address of a hyper-cube is determined by content of corresponding image patch. Then, each image patch is stored using corresponding address as a key into the database. Later, for a target image patch, a set of similar image patches is retrieved from the database using the key.

Link: https://patents.google.com/patent/US10176202B1





