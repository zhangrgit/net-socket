package com.rz.client_to_client;

import java.io.Serializable;

public class Message implements Serializable {
	
	private static final long serialVersionUID = 1L;

	// 客户端编号
    int id;

    // 消息内容
    String msg;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
