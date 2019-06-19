package com.itcast.learn04;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Callable;

public class MyThread extends  Thread {

    List<String> strings = new ArrayList<>();
    @Override
    public void run() {
        for (int i=0;i<100000;i++){
            strings.add(UUID.randomUUID().toString());
        }
    }
}