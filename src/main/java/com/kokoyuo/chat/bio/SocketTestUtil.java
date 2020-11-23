package com.kokoyuo.chat.bio;

import org.fusesource.jansi.Ansi;

import java.util.Random;

/**
 * @author lixuanwen
 * @date 2020-11-04 11:44
 */
public class SocketTestUtil {

    public static void main(String[] args) {
        Ansi.Color randomColor = findRandomColor();
        System.out.println(Ansi.ansi().bg(randomColor).a("测试数据").reset());

        Ansi.Color[] values = Ansi.Color.values();
        for (int i = 0; i <values.length ; i++) {
            System.out.println(Ansi.ansi().fg(values[i]).a("测试数据"+i).reset());
        }
    }

    public static Ansi.Color findRandomColor(){
        Random rd = new Random();
        int i = rd.nextInt(9);
        return Ansi.Color.values()[i];
    }

    public static void out(String s){
        ClientUser localUser = ClientContext.findLocalUser();
        String outStr = s;

        if (SocketConfig.CHAT_MODE == 0){

        } else {
            if (localUser != null){
                Ansi msg = Ansi.ansi().fg(localUser.getColor()).a(s).reset();
                outStr = msg.toString();
            }
        }

        System.out.println(outStr);
        MessageUtil.addMsg2Content(localUser, outStr);
    }
}
