package org.wso2.carbon.selfhealing;

import org.wso2.carbon.server.admin.common.IServerAdmin;

public class SingletonIService {
    private static SingletonIService singletonIService = null;
    private IServerAdmin iServerAdmin = null;
    protected SingletonIService (){

    }
    public static SingletonIService getInstance (){
        if(singletonIService==null){
            singletonIService  = new SingletonIService();
        }
        return singletonIService;
    }

    public void setIServerAdmin(IServerAdmin iServerAdmin){
        this.iServerAdmin = iServerAdmin;
    }

    public IServerAdmin getIServerAdmin (){
        return iServerAdmin;
    }
}
