package com.kokoyuo.chat.bio;

/**
 * @author lixuanwen
 * @date 2020-11-04 13:43
 */
public class ClientContext {

    private static ThreadLocal<ClientUser> socketContext = new ThreadLocal<>();

    public static void initContext(ClientUser user){
        socketContext.set(user);
    }

    public static void clearContext(){
        socketContext.remove();
    }

    public static ClientUser findLocalUser(){
        return socketContext.get();
    }
}
