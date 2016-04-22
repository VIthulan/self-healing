package org.wso2.carbon.selfhealing;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.ComponentContext;
import org.wso2.carbon.server.admin.common.IServerAdmin;

import javax.management.*;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.List;

/**
 * This class represents the analytics data service declarative services component.
 *
 * @scr.component name="self.healing" immediate="true"
 * @scr.reference name="serveradmin.service.component" interface="org.wso2.carbon.server.admin.common.IServerAdmin"
 * cardinality="1..1" policy="dynamic"  bind="setIServerAdmin" unbind="unsetIServerAdmin"
 */
public class SHCore {

    private static final Log log = LogFactory.getLog(SHCore.class);
    protected void activate(ComponentContext ctx) {
        BundleContext bundleContext = ctx.getBundleContext();
        try {
            List<GarbageCollectorMXBean> gcBeans = ManagementFactory.getGarbageCollectorMXBeans();
            for (GarbageCollectorMXBean gcBean : gcBeans) {
                NotificationEmitter emitter = (NotificationEmitter) gcBean;
                GCHandler gcHandler = new GCHandler();
                emitter.addNotificationListener(gcHandler, null, null);
            }
            log.info("Self healing successfully activated");
        } catch (Throwable e) {

        }
    }

    protected void setIServerAdmin (IServerAdmin iServerAdmin){
        SingletonIService singletonIService = SingletonIService.getInstance();
        singletonIService.setIServerAdmin(iServerAdmin);
    }

    protected void unsetIServerAdmin (IServerAdmin iServerAdmin){
        SingletonIService singletonIService = SingletonIService.getInstance();
        singletonIService.setIServerAdmin(null);
    }


}
