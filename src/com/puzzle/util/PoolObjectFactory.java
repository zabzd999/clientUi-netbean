/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.puzzle.util;

import com.puzzle.module.send.excutor.MessageSendExcutor;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.commons.pool2.impl.GenericObjectPool;

/**
 *
 * @author ljs
 */
public class PoolObjectFactory extends BasePooledObjectFactory<MessageSendExcutor> {

    @Override
    public MessageSendExcutor create() throws Exception {
        return new MessageSendExcutor();
    }

    @Override
    public PooledObject<MessageSendExcutor> wrap(MessageSendExcutor t) {
        return new DefaultPooledObject<MessageSendExcutor>(t);
    }

//    public static final GenericObjectPool<MessageSendExcutor> pool = new GenericObjectPool<MessageSendExcutor>(new PoolObjectFactory());

    static {

//        pool.setMaxTotal(Integer.parseInt(ConfigUtils.config.getProperty("pool.size")));
    }

}
