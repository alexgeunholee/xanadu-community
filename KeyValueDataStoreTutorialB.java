import com.xanadu.*;
import com.xanadu.util.*;
import com.xanadu.client.*;
import com.xanadu.server.*;
import com.xanadu.store.*;

import java.util.*;
import java.io.*;
import java.net.*;
import java.nio.*;

public class KeyValueDataStoreTutorialB
{
    private static final int BYTES_IN_MB = 1024 * 1024;
    private static final int numReplicas = 2;
    private static final boolean use_Checksums = true;
    private static final int block_Size = 2;
    private static final DataStoreURI storeURI = new DataStoreURI("TestKVDS");

    private static int Replicas;
    private static int blockSize;

    private static DataStore dataStore;
    private static DataBlockStore dbs;
    private static KeyValueStore kvs;

    private static InetSocketAddress[] registryAddresses;

    private static List<KeyValue> knownKeyValues;

    public static void main(String[] args) throws Exception
    {
        ConfigHandler cfg = ConfigHandler.buildConfigFromArgs("KeyValueDataStoreTutorialB", args);
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

        String keyName = "TestKeySetB";
        String msg = "TestDataSetB";
        int numUpdates = 10;
        SetValueTask[] tasks = new SetValueTask[numUpdates];
        String[] Knownkey = new String[numUpdates];
        long[] Knowntime = new long[numUpdates];

        for (int i=0; i < numUpdates; i++)
        {
            String key = keyName + i;
            Knownkey[i] = key;
            byte[] storedData = ResourceUtils.getBytes(msg + i);
            long references = dataStore.storeData(storedData.length, new ByteArrayInputStream(storedData));
            long currentTimeNanos = ReferenceTools.nowNanos();
            tasks[i] = new SetValueTask(key, currentTimeNanos, storeURI, references);
            /**
             Define task for bulk Key-Value Store insert
             SetValueTask objects act like JDBC ResultSet object, but each object specifies an individual key-value store update we wish to make
             */
        }

        kvs.setValues(tasks, 0, numUpdates);
        /**
         Bulk insert into the key-value store
         setValues(SetValueTask[] tasks, int offset, int length)
         A convenience method for multiple calls of the 'setValue' method, which may be more efficient than looping around
         multiple separate calls. Note this call does NOT offer transactional guarantees, and is consequently faster than an
         equivalent 'setValuesAtomic' call.
         The results of the call can be retrieved from the 'tasks' array when this call returns. NOTE that the store reserves the right to
         reorder the tasks in this array during the call, so while the call is executing ensure that no other threads are accessing and/or
         modifying the array
         */
        knownKeyValues = new ArrayList<KeyValue>();
        for (int i=0; i < numUpdates; i++)
        {
            Knowntime[i] = tasks[i].getNanoTimeResult();
            //Gets the resulting time that the Xanadu store chose for this task - or throws an exception that occurred during the call if this failed for any reason.
            System.out.println("Stored reference for key '" + Knownkey[i] + "' at Known Time " + ReferenceTools.nanoDate(Knowntime[i]));
            knownKeyValues.add(tasks[i].getKeyValue());
            //Returns a KeyValue object that represents the result of the task, if successful
        }

        for (int i = 0; i < knownKeyValues.size(); i++)
            System.out.println("Known Key Values: " + i + "  " + knownKeyValues.get(i));

        dataStore.close();
        kvs.close();

        System.exit(0);
    }

}