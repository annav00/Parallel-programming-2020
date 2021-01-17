package main.java.ru.spbstu.telematics;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.Exchanger;

public class App {
    public static void main(String[] args) {
    Semaphore sw = new Semaphore(1);
    Exchanger<String> exO = new Exchanger<String>();
    Exchanger<String> exSR = new Exchanger<String>();

    Generator generator = new Generator(exO, exSR);
    Switch switchO = new Switch(exO, sw);
    Scan scan = new Scan(exSR, sw);

    ExecutorService executorService = Executors.newFixedThreadPool(3);
    executorService.submit(generator);
    executorService.submit(switchO);
    executorService.submit(scan);
    }
}