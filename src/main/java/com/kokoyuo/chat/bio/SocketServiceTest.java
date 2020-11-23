package com.kokoyuo.chat.bio;

import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;

import static com.kokoyuo.chat.bio.MessageUtil.SYS;
import static com.kokoyuo.chat.bio.MessageUtil.chatContent;
import static com.kokoyuo.chat.bio.SocketTest.writeln;
import static com.kokoyuo.chat.bio.SocketTestUtil.out;

/**
 * @author lixuanwen
 * @date 2020-10-29 11:44
 */
public class SocketServiceTest {
    public static void main(String[] args) throws IOException {
        System.out.println("聊天服务器start----------->");
        ServerSocket serverSocket = new ServerSocket(7099);
        while (true){
            // 收到消息
            Socket accept = serverSocket.accept();
            initSocketClient(accept);
        }
    }

    public static void initSocketClient(Socket accept){
        ClientUser user;
        try{
            InputStream inputStream = accept.getInputStream();
            user = getClientUser(inputStream);
        } catch (Exception e){
            return;
        }
        // 消息接收者
        Thread receiver = new Thread(() -> {
            try{
                InputStream inputStream = accept.getInputStream();
                ClientContext.initContext(user);
                disposeReceiveMsg(inputStream);
                inputStream.close();
            }catch (Exception e){
                e.printStackTrace();
            } finally {
                ClientContext.clearContext();
            }
        });
        receiver.start();

        // 消息发送者
        Thread sender = new Thread(() -> {
            try{
                ClientContext.initContext(user);
                OutputStream outputStream = accept.getOutputStream();
                sendMsg(outputStream);
                outputStream.close();
            }catch (Exception e){
                e.printStackTrace();
            } finally {
                ClientContext.clearContext();
            }
        });
        sender.start();
    }

    public static void disposeReceiveMsg(InputStream inputStream) throws IOException {
        BufferedReader read=new BufferedReader(new InputStreamReader(inputStream, SocketConfig.charset));
        ClientUser user = ClientContext.findLocalUser();
        String line;
        while ((line = read.readLine()) != null) {
            //注意指定编码格式，发送方和接收方一定要统一，建议使用UTF-8
            say(user, line);
            if (SocketConfig.GOODBYE.equalsIgnoreCase(line)){
                clientExit(user);
            }
        }
    }

    public static void sendMsg(OutputStream outputStream) throws IOException {
        int cursor = 0;
        ClientUser localUser = ClientContext.findLocalUser();
        while (true){
            if (chatContent.size() > cursor){
                for (;cursor < chatContent.size(); cursor++) {
                    SystemMessage systemMessage = chatContent.get(cursor);
                    if (!Objects.equals(systemMessage.getUser(), localUser)){
                        outputStream.write(systemMessage.getMsg().getBytes(SocketConfig.charset));
                        writeln(outputStream);
                    }
                }
            }
        }
    }

    public static void say(ClientUser user, String msg){
        out(user.getName()+"说:");
        out(msg);
    }

    public static ClientUser getClientUser(InputStream inputStream){
        BufferedReader read=new BufferedReader(new InputStreamReader(inputStream, SocketConfig.charset));
        String clientUser = null;
        try {
            clientUser = read.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ClientUser user = JSONObject.parseObject(clientUser, ClientUser.class);
        user.setColor(SocketTestUtil.findRandomColor());
        MessageUtil.addMsg2Content(SYS, "欢迎["+user.getName()+"]进入聊天室");
        return user;
    }

    public static void clientExit(ClientUser user){
        System.out.println("客户端["+ user.getName() +"]退出聊天室");
    }
}
