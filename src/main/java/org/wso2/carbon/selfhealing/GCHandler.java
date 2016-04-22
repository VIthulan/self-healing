package org.wso2.carbon.selfhealing;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.server.admin.common.IServerAdmin;

import javax.management.Notification;
import javax.management.NotificationListener;


public class GCHandler implements NotificationListener {
    private static final Log log = LogFactory.getLog(GCHandler.class);
    int count = 0;
    MemoryManager memoryManager = new MemoryManager();
    public static final String GARBAGE_COLLECTION_NOTIFICATION = "com.sun.management.gc.notification";

    public void collectNotification() {

    }


    @Override
    public void handleNotification(Notification notification, Object handback) {
        /*if(count ==0){
            new Tester("Hello");
            count++;
        }*/
        log.info("Notification Received");
        /*SingletonIService singletonIService = SingletonIService.getInstance();
        IServerAdmin iServerAdmin = singletonIService.getIServerAdmin();
        try {
            iServerAdmin.restartGracefully();
            log.info("Restarting the JVM");
        } catch (Exception e) {
            log.error(e.getMessage());
            //e.printStackTrace();
        }*/
        String notifType = notification.getType();
        long mb = 1024*1024;

        if (notifType.equals(GARBAGE_COLLECTION_NOTIFICATION)) {
            //CompositeData compositeData = (CompositeData) notification.getUserData();
            //GarbageCollectionNotificationInfo gcInformation = GarbageCollectionNotificationInfo.from(compositeData);
            //GcInfo gcInfo = gcInformation.getGcInfo();

            long usedMemory = memoryManager.getUsedMemory();
            long maxMemory = memoryManager.getMaxMemory();
            if(usedMemory>(maxMemory*0.75)){
                long currentTimeInMillis = System.currentTimeMillis();
                memoryManager.handleMemoryInfo(usedMemory,currentTimeInMillis);
            }
            if(usedMemory>(maxMemory*0.8)){
                memoryManager.checkMemoryPattern(5);
            }

        }
    }
}
