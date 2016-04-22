package org.wso2.carbon.selfhealing;

import java.lang.management.ManagementFactory;
import java.util.List;

public class JVMManager {

    public String getJVMVersion() {
        return ManagementFactory.getRuntimeMXBean().getVmVersion();
    }

    public List<String> getInputArguments() {
        return ManagementFactory.getRuntimeMXBean().getInputArguments();
    }

}
