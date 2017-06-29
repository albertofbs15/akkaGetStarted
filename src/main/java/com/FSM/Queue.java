package com.FSM;

/**
 * Created by AHernandezS on 29/06/2017.
 */
public class Queue {
    private final Object obj;

    public Queue(Object obj) {
        this.obj = obj;
    }

    public Object getObj() {
        return obj;
    }

    @Override
    public String toString() {
        return "Queue{" +
                "obj=" + obj +
                '}';
    }
}
