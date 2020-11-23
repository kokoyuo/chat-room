package com.kokoyuo.chat.bio;

import java.util.Vector;

/**
 * @author lixuanwen
 * @date 2020-11-04 15:33
 */
public class MessageUtil {

    public static volatile Vector<SystemMessage> chatContent = new Vector<>(1024*1024);

    public static ClientUser SYS = new ClientUser();

    static {
        SYS.setName("系统");
    }

    public static void addMsg2Content(ClientUser localUser, String s){
        SystemMessage msg = new SystemMessage();
        msg.setMsg(s);
        msg.setUser(localUser);
        chatContent.add(msg);
    }
}
