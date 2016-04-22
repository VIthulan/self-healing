package org.wso2.carbon.selfhealing;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.server.admin.common.IServerAdmin;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class MemoryManager {

    private static final Log log = LogFactory.getLog(MemoryManager.class);

    final long mb = 1024*1024;
    Runtime runtime;
    long lastUsedMemory = 0;
    long lastEndTime = 0;
    List<BigDecimal> firstDerivativeList = new ArrayList<BigDecimal>();
    List<BigDecimal> secondDerivativeList = new ArrayList<BigDecimal>();
    public MemoryManager () {
        runtime = Runtime.getRuntime();
    }

    public long getUsedMemory () {
        return (runtime.totalMemory() - runtime.freeMemory())/mb;
    }

    public long getFreeMemory () {
       return runtime.freeMemory()/mb;
    }

    public long getTotalMemory () {
        return runtime.totalMemory()/mb;
    }

    public long getMaxMemory () {
        return runtime.maxMemory()/mb;
    }

    public void handleMemoryInfo(Long usedMemory,Long endTime) {

        BigDecimal timediff = new BigDecimal(endTime-lastEndTime);
        BigDecimal milliSecond = new BigDecimal(1000);
        BigDecimal timeDifInSec = timediff.divide(milliSecond,10, RoundingMode.HALF_UP);

        BigDecimal memoryDiff = new BigDecimal(usedMemory-lastUsedMemory);
        BigDecimal firstDerivative = memoryDiff.divide(timeDifInSec,10,RoundingMode.HALF_UP);
        firstDerivativeList.add(firstDerivative);

       if(firstDerivativeList.size()>=2) {
           int listCount = firstDerivativeList.size();
           BigDecimal firstDerDiff = firstDerivativeList.get(listCount-1).subtract(firstDerivativeList.get(listCount-2));
           BigDecimal secondDerivative = firstDerDiff.divide(timeDifInSec,10,RoundingMode.HALF_UP);
           secondDerivativeList.add(secondDerivative);
        }

        lastUsedMemory = usedMemory;
        lastEndTime = endTime;
    }

    public void checkMemoryPattern(int count) {
        int firstDerivativeCount = firstDerivativeList.size();
       // System.out.println("first count "+firstDerivativeCount);
        int positive = 0;
        int negetive = 0;
        boolean is1stPositive =false;
        for(int i=firstDerivativeCount-1;i > (firstDerivativeCount-count);i--){
           // System.out.println("First derivative "+firstDerivativeList.get(i));
           // double diff = firstDerivativeList.get(i)  - firstDerivativeList.get(i-1);
            int result = firstDerivativeList.get(i).compareTo(BigDecimal.ZERO);
            if(result>0){
                is1stPositive =true;
            }
            else{
               is1stPositive =false;
            }
        }
       // System.out.println("First Derivative postive: "+positive+" negetive: "+negetive);
        if(is1stPositive){          //Can add more filtering options
            int secondDerivativeCount = secondDerivativeList.size();
           // System.out.println("Second count "+secondDerivativeCount);
            int positive2nd = 0;
            int negetive2nd = 0;
            boolean is2ndPositive = false;
            for(int i = secondDerivativeCount-1; i > (secondDerivativeCount-count);i--){
              //  System.out.println("Second derivative "+secondDerivativeList.get(i));
                int result = secondDerivativeList.get(i).compareTo(BigDecimal.ZERO);
                if(result>=0){
                    is2ndPositive = true;
                    positive2nd++;
                }
                else{
                    is2ndPositive = false;
                    negetive2nd++;
                }
            }
           // System.out.println("Second Derivative positive: "+positive2nd+" negetive: "+negetive2nd);
            if(is2ndPositive){
                SingletonIService singletonIService = SingletonIService.getInstance();
                IServerAdmin iServerAdmin = singletonIService.getIServerAdmin();
                log.info("===RESTART THE JVM=== 1st Priority");
                try {
                    iServerAdmin.restartGracefully();
                } catch (Exception e) {
                    log.error(e.getMessage());
                   // e.printStackTrace();
                }
               // System.out.println("===RESTART THE JVM=== 1st Priority");   //If all derivatives are positive
            }
            else if(positive2nd>negetive2nd){
                log.info("===RESTART THE JVM=== 2nd Priority");
                //System.out.println("===RESTART THE JVM=== 2nd Priority");      //positive derivative > negative derivative
            }
            else{
                log.info("===RESTART THE JVM=== 3rd Priority");
               // System.out.println("===RESTART THE JVM=== 3rd Priority");       //negative derivative > positive derivative
            }

        }
     //   System.out.println();
    }
}
