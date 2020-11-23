package com.kokoyuo.chat.bio;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.fusesource.jansi.Ansi;

import java.io.Serializable;

/**
 * @author lixuanwen
 * @date 2020-10-30 13:51
 */
@EqualsAndHashCode
@Data
public class ClientUser implements Serializable {

    private String name;

    private String hostAddress;

    private int port;

    private Ansi.Color color;
}
