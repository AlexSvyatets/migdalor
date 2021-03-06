package com.liveperson.migdalor.example;/**
 * Created with IntelliJ IDEA.
 * User: ehudl
 * Date: 9/24/13
 * Time: 6:15 PM
 * To change this template use File | Settings | File Templates.
 */


import com.liveperson.schema.SchemaRepo;
import com.liveperson.schema.impl.InMemSchemaRepo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Queue;
import java.util.concurrent.*;

public class SimpleMain {
    /**
     * slf4j Logger
     */
    private static final Logger LOG = LoggerFactory.getLogger(SimpleMain.class);
    public static final int TIME_TO_RUN = 10 * 1000;


    public static void main(String[] args) throws InterruptedException, ClassNotFoundException {
        SchemaRepo<String> schemaRepo = new InMemSchemaRepo<String>();
        Queue<byte []> concurrentLinkedQueue = new ConcurrentLinkedQueue<byte []>();
        run(schemaRepo, concurrentLinkedQueue, TIME_TO_RUN);


    }

    public static void run(SchemaRepo<String> schemaRepo, Queue<byte[]> concurrentLinkedQueue, long sleep) throws InterruptedException, ClassNotFoundException {
        //ExecutorService executor = Executors.newFixedThreadPool(2);
        Thread prod = ExampleProducer.runProducer(concurrentLinkedQueue,schemaRepo);
        //ExampleProducer exampleProducer = new ExampleProducer(concurrentLinkedQueue, schemaRepo);
        Thread cons = ExampleConsumer.runConsumer(concurrentLinkedQueue, schemaRepo);

        Thread.sleep(sleep);

        prod.interrupt();
        cons.interrupt();


    }



}
