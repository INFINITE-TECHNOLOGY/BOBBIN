package io.infinite.bobbin

import java.util.concurrent.ConcurrentHashMap

/**
 * Application servers interfere with normal Java ThreadLocal
 * Thus we have to use custom class.
 *
 * */
class ThreadLocal {

    public static ConcurrentHashMap<Thread, Object> objectsByThread = new ConcurrentHashMap<Thread, Object>()

    static void set(Object iObject){
        objectsByThread.put(Thread.currentThread(), iObject)
    }

    static Object get(){
        objectsByThread.get(Thread.currentThread())
    }

}