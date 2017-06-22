package com.streams;

import akka.Done;
import akka.NotUsed;
import akka.actor.ActorSystem;
import akka.stream.ActorMaterializer;
import akka.stream.IOResult;
import akka.stream.Materializer;
import akka.stream.ThrottleMode;
import akka.stream.javadsl.*;
import akka.util.ByteString;
import scala.concurrent.duration.Duration;

import java.math.BigInteger;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Created by AHernandezS on 21/06/2017.
 */
public class Main {

    public void Stream() {
        final ActorSystem system = ActorSystem.create("QuickStart");
        final Materializer materializer = ActorMaterializer.create(system);

        final Source<Integer, NotUsed> source = Source.range(1, 100);

        //CompletionStage<Done> done = source.runForeach(i -> System.out.println(i), materializer);


        final Source<BigInteger, NotUsed> factorials =
                source.scan(BigInteger.ONE, (acc, next) -> acc.multiply(BigInteger.valueOf(next)));

        //final CompletionStage<IOResult> result =
//                factorials
//                        .map(num -> ByteString.fromString(num.toString() + "\n"))
//                        .runWith(FileIO.toPath(Paths.get("factorials.txt")), materializer);


     //   factorials.map(BigInteger::toString).runWith(lineSink("factorial2.txt"), materializer);

        factorials
                .zipWith(Source.range(0, 99), (num, idx) -> String.format("%d! = %s", idx, num))
                .throttle(1, Duration.create(1, TimeUnit.SECONDS), 1, ThrottleMode.shaping())
                .runForeach(s -> System.out.println(s), materializer);
        //done.thenRun(() -> system.terminate());
    }

    public Sink<String, CompletionStage<IOResult>> lineSink(String filename) {
        return Flow.of(String.class)
                .map(s -> ByteString.fromString(s.toString() + "\n"))
                .toMat(FileIO.toPath(Paths.get(filename)), Keep.right());
    }

    public static void main (String[] args) {
        new Main().Stream();
    }



    public static class Author {
        public final String handle;

        public Author(String handle) {
            this.handle = handle;
        }

        // ...

    }

    public static class Hashtag {
        public final String name;

        public Hashtag(String name) {
            this.name = name;
        }

        // ...
    }

    public static class Tweet {
        public final Author author;
        public final long timestamp;
        public final String body;

        public Tweet(Author author, long timestamp, String body) {
            this.author = author;
            this.timestamp = timestamp;
            this.body = body;
        }

        public Set<Hashtag> hashtags() {
            return Arrays.asList(body.split(" ")).stream()
                    .filter(a -> a.startsWith("#"))
                    .map(a -> new Hashtag(a))
                    .collect(Collectors.toSet());
        }

        // ...
    }

    public static final Hashtag AKKA = new Hashtag("#akka");
}
