package com.streams;

import akka.NotUsed;
import akka.actor.ActorSystem;
import akka.stream.ActorMaterializer;
import akka.stream.Materializer;
import akka.stream.javadsl.Sink;
import akka.stream.javadsl.Source;
import scala.concurrent.duration.FiniteDuration;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;

/**
 * Created by AHernandezS on 18/07/2017.
 */
public class TestGroupedWhitin {

    final ActorSystem system = ActorSystem.create("reactive-tweets");
    final Materializer mat = ActorMaterializer.create(system);

    int numGroupedElements = 3;
    FiniteDuration duration = new FiniteDuration(1, TimeUnit.SECONDS);

    public void example2() {
        final Source<Integer, NotUsed> source =
                Source.from(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)).groupedWithin(numGroupedElements, duration).mapAsync(10, messages -> {
           return CompletableFuture.completedFuture(messages.stream().mapToInt(i -> i).sum());
        });

        source.runWith(Sink.foreach(number -> System.out.println(number)), mat);
    }

    public static void main (String... args){
        new TestGroupedWhitin().example2();
    }
}
