package io.infinite.bobbin


import java.util.concurrent.ConcurrentHashMap

class BobbinThreadLocal {

    static ConcurrentHashMap<Thread, Bobbin> bobbinsByThread = new ConcurrentHashMap<Thread, Bobbin>()

    static void set(Bobbin bobbin) {
        bobbinsByThread.put(Thread.currentThread(), bobbin)
    }

    static Bobbin get() {
        bobbinsByThread.get(Thread.currentThread())
    }

    static void clear() {
        bobbinsByThread.clear()
    }

}
