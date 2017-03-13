package com.artist.myworld.rpc.impl;

import com.artist.myworld.rpc.UserRpc;
import com.dili.utils.domain.BaseOutput;

/**
 * Created by asiam on 2017/3/10 0010.
 */
public class UserRpcImpl implements UserRpc {
    private String name;

    public String hello(String str) {
        System.out.println("hello:"+name+","+str);
        return str;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public BaseOutput updateSellerPasswordByPhoneNumber(String str) {
        return null;
    }
}
