package com.kokoyuo.chat;

import com.kokoyuo.chat.bio.SocketServiceTest;
import com.kokoyuo.chat.bio.SocketTest;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        System.out.println("-------------");
        if (null == args || args.length == 0){
            SocketTest.main(null);
        } else {
            String arg = args[0];
            if ("Service".equalsIgnoreCase(arg)){
                SocketServiceTest.main(null);
            } else {
                SocketTest.main(null);
            }
        }
    }
}
