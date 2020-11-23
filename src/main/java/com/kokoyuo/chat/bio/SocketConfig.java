package com.kokoyuo.chat.bio;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author lixuanwen
 * @date 2020-10-29 14:14
 */
public class SocketConfig {

    public static final String GOODBYE = "goodbye";

    public static final String EXIT_COMMAND = "exit";

    public static final Charset charset = StandardCharsets.UTF_8;

    /**
     * 0 表示基础模式
     */
    public static final int CHAT_MODE = 0;
}
