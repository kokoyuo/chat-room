package com.kokoyuo.chat.bio;

import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author lixuanwen
 * @date 2020-10-29 11:40
 */
public class SocketTest {

    public static void main(String[] args) throws IOException {
        System.out.println("聊天客户端启动--------------------->");
        Socket socket = new Socket("47.106.136.142", 7099);
        OutputStream outputStream = socket.getOutputStream();
        initReceiver(socket);
        Scanner scanner = new Scanner(System.in);
        // 注册用户
        register(outputStream, scanner);
        while (true){
            String enterInStr = scanner.nextLine();
            outputStream.write(enterInStr.getBytes(SocketConfig.charset));
            writeln(outputStream);
            if (SocketConfig.EXIT_COMMAND.equalsIgnoreCase(enterInStr)){
                System.out.println(SocketConfig.GOODBYE);
                outputStream.write(SocketConfig.GOODBYE.getBytes(SocketConfig.charset));
                writeln(outputStream);
                outputStream.close();
                socket.close();
                break;
            }
        }
        System.out.println("------------------客户端退出");
    }

    public static void register(OutputStream outputStream, Scanner scanner) throws IOException {
        System.out.println("请输入你的用户名--------------------->");
        String enterInStr = scanner.nextLine();
        ClientUser clientUser = new ClientUser();
        clientUser.setName(enterInStr);
        outputStream.write(JSONObject.toJSONString(clientUser).getBytes(SocketConfig.charset));
        writeln(outputStream);
        System.out.println("进入聊天室成功--------------------->");
    }

    public static void writeln(OutputStream outputStream){
        try {
            outputStream.write("\r\n".getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void initReceiver(Socket socket) throws IOException {
        InputStream inputStream = socket.getInputStream();
        Thread receiver = new Thread(() -> {
           try {
               receiveMsg(inputStream);
           } catch (Exception e){
               e.printStackTrace();
           }
        });
        receiver.start();
    }

    public static void receiveMsg(InputStream inputStream) throws IOException {
        BufferedReader read=new BufferedReader(new InputStreamReader(inputStream, SocketConfig.charset));
        while (true) {
            String line = read.readLine();
            //注意指定编码格式，发送方和接收方一定要统一，建议使用UTF-8
            if (line != null){
                System.out.println(line);
            }
        }
    }
}
