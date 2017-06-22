package com.streams;

import akka.NotUsed;
import akka.actor.ActorSystem;
import akka.stream.ActorMaterializer;
import akka.stream.Materializer;
import akka.stream.javadsl.Sink;
import akka.stream.javadsl.Source;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by AHernandezS on 21/06/2017.
 */
public class Tweets {

    final ActorSystem system = ActorSystem.create("reactive-tweets");
    final Materializer mat = ActorMaterializer.create(system);

    public Tweets() {

        Source<Tweet, NotUsed> tweets = Source.empty();

        final Source<Author, NotUsed> authors =
                tweets
                        .filter(t -> t.hashtags().contains(AKKA))
                        .map(t -> t.author);

        authors.runWith(Sink.foreach(a -> System.out.println(a)), mat);
        //authors.runForeach(a -> System.out.println(a), mat);



        final Source<Hashtag, NotUsed> hashtags =
                tweets.mapConcat(t -> new ArrayList<Hashtag>(t.hashtags()));
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

    public static void main (String ... args) {
        new Tweets();
    }
}
