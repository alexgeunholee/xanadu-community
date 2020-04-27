import com.xanadu.*;
import com.xanadu.util.*;
import com.xanadu.client.*;
import com.xanadu.server.*;
import com.xanadu.store.*;

import java.util.*;
import java.io.*;
import java.net.*;
import java.nio.*;

public class KeyValueDataStoreTutorialA
{
    private static final int BYTES_IN_MB = 1024 * 1024;
    private static final int numReplicas = 2;
    private static final boolean use_Checksums = true;
    private static final int block_Size = 2;
    private static final DataStoreURI storeURI = new DataStoreURI("TestKVDS");

    private static int Replicas;
    private static int lookupLimit;
    private static int blockSize;

    private static DataStore dataStore;
    private static DataBlockStore dbs;
    private static KeyValueStore kvs;

    private static InetSocketAddress[] registryAddresses;

    public static void main(String[] args) throws Exception
    {
        ConfigHandler cfg = ConfigHandler.buildConfigFromArgs("KeyValueDataStoreTutorialA", args);
        //com.xanadu.util.ConfigHandler
        registryAddresses = cfg.getIPAddresses("registries", DistributedRegistry.DEFAULT_PORT);
        //Get the IP addresses where the Xanadu Registries are running
        //com.xanadu.server.DistributedRegistry
        Replicas = cfg.getInt("replicas", numReplicas);
        blockSize = cfg.getInt("blocksize", block_Size);
        blockSize = blockSize * BYTES_IN_MB;

        kvs = XanaduConnectionFactory.createKeyValueStore(registryAddresses);
        //Xanadu key-value store client connection
        dbs = XanaduConnectionFactory.createDataBlockStore(registryAddresses, storeURI, Replicas, use_Checksums);
        dataStore = XanaduConnectionFactory.createDataStore(dbs, blockSize);
        //Xanadu data store client connection

        String keyName = "TestKeySetA";
        String msg = "TestDataSetA";
        int numUpdates = 10;
        String[] Knownkey = new String[numUpdates];
        long[] Knowntime = new long[numUpdates];

        for (int i=0; i < numUpdates; i++)
        {
            String key = keyName + i;
            Knownkey[i] = key;
            byte[] storedData = ResourceUtils.getBytes(msg + i);
            //Convert input data to bytes
            //com.xanadu.util.ResourceUtils
            long references = dataStore.storeData(storedData.length, new ByteArrayInputStream(storedData));
            //Store value-data in data store
            long currentTimeNanos = ReferenceTools.nowNanos();
            //Get system-time: the desired time of the update in nano-seconds in POSIX/Epoch time.
            //com.xanadu.util.ReferenceTools
            long storeTimeNanos = kvs.setValue(key, currentTimeNanos, storeURI, references);
            //Store value-data behind key
            /**
             setValue(String key, long timeHintNanos, DataStoreURI storeURI, long dataStoreReference)
             Sets the value of the given key to those supplied - a reference to a datastore and a (long) data reference within that store.
             The timeHint parameter is a hint to the system and as such the store will allocate a time internally as the next available time
             permitted. All writes are strictly ordered in time, and the precise value chosen is returned by this call.
             */
            Knowntime[i] = storeTimeNanos;
            System.out.println("Stored reference for key '" + key + "' at time " + ReferenceTools.nanoDate(storeTimeNanos));

            long testReference = dbs.lookupDataBlock(storedData, 0, storedData.length);
            //The data store can tell if it already contains a block of data
            System.out.println("Looked up data: found reference '0x" + Long.toHexString(testReference) + "' at time " + Long.toHexString(storeTimeNanos));
        }

        System.out.println("**** Query Using Known Updated Time ****");
        long startupdateTimeNanos = Knowntime[0];
        long endupdateTimeNanos = Knowntime[numUpdates-1];
        System.out.println("Time Bounds: " + Long.toHexString(startupdateTimeNanos) + "  to  " + Long.toHexString(endupdateTimeNanos));

        KeyIterator kit = kvs.getKeys(startupdateTimeNanos-1, endupdateTimeNanos+1);
        /**
         getKeys(long minTimeNanos, long maxTimeNanos)
         Returns an iterator that enables scanning through the values of all keys within a given time bounds. Using an iterator is
         potentially more efficient than repeated calls to 'getNextKeyInPeriod'. NOTE: the time range is exclusive - the values
         returned by the iterator will not include updates at exactly 'minTimeNanos' or 'maxTimeNanos'.
         */
        String key = null;
        while ((key = kit.next()) != null)
        {
            //Iterate through keys
            System.out.println("Found update(s) for key "+ key);
            ConstantKeyValueIterator itt = kvs.getKeyValues(key, startupdateTimeNanos-1, endupdateTimeNanos+1);
            /**
             getKeyValues(String key, boolean ascending, long startTimeNanos, long endTimeNanos)
             Returns an iterator that enables scanning through the values of a key within a given time bounds. The iterator guarantees
             increasing time ordering on the values returned. Using an iterator is potentially more efficient than repeated calls to
             'getValueAfter'. Note 'ascending' must be true and the iterator will be initially positioned at the start of the time range, so is
             ready for looping through values by repeated calls to next(). Support for descending iterators has been deprecated. NOTE:
             the time range is exclusive - the values returned by the iterator will not include updates at exactly 'startTimeNanos' or
             'endTimeNanos'
             */
            KeyValue kv = null;
            while ((kv = itt.next()) != null)
                //Iterate through the time series of key-value updates for key.
                System.out.println("Found key-value update: "+ kv);
        }

        System.out.println("**** Query Using Known Key Name ****");
        long startTime = 0;
        long finishTime = Long.MAX_VALUE;
        //Long.MAX_VALUE = 9,223,372,036,854,775,807
        for (int i=0; i < numUpdates; i++)
        {
            KeyValue kv = kvs.getValueBefore(Knownkey[i], startTime, finishTime);
            /**
             getValueBefore(String key, long startTimeNanos, long endTimeNanos)
             Returns the last value alteration of a key before the supplied end time(measured in Xanadu nanoseconds).
             Returns null if there are no changes in a keys' value within the given range.
             Note the range bounds - a value specified exactly at 'endTimeNanos' will not be returned,
             but one at exactly 'startTimeNanos' will be.
             */
            System.out.println("Query returned key-value " + kv);

            long timeAdded = kv.getTimeMillis();
            long retrievedReference = kv.reference;
            long len = dataStore.getDataSize(retrievedReference);
            byte[] datastored = new byte[(int) len];

            InputStream data = dataStore.readData(retrievedReference);
            //Query the data store for the data associated with the key-value's reference.
            DataInputStream din = new DataInputStream(data);
            din.readFully(datastored);
            din.close();

            System.out.println("Retrieved message '" + ResourceUtils.createString(datastored) + "' for key '" + Knownkey[i] + "' that was updated at time " + new Date(timeAdded));
        }

        dataStore.close();
        kvs.close();
        //Close the store connections

        System.exit(0);

        }
}
