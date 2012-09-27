package org.jbpm.task.service.hornetq.sync;

import org.drools.SystemEventListenerFactory;
import org.jbpm.task.service.AsyncTaskServiceWrapper;
import org.jbpm.task.service.TaskClient;
import org.jbpm.task.service.base.sync.TaskServiceTaskAttributesBaseSyncTest;
import org.jbpm.task.service.hornetq.HornetQTaskClientConnector;
import org.jbpm.task.service.hornetq.HornetQTaskClientHandler;
import org.jbpm.task.service.hornetq.HornetQTaskServer;

public class TaskServiceTaskAttributesHornetQSyncTest extends TaskServiceTaskAttributesBaseSyncTest {

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        server = new HornetQTaskServer(taskService, 5446);
        Thread thread = new Thread(server);
        thread.start();
        logger.debug("Waiting for the HornetQTask Server to come up");
        while (!server.isRunning()) {

            Thread.sleep(50);
        }

        client = new AsyncTaskServiceWrapper(new TaskClient(new HornetQTaskClientConnector("client 1",
                new HornetQTaskClientHandler(SystemEventListenerFactory.getSystemEventListener()))));
        client.connect("127.0.0.1", 5446);
    }

}