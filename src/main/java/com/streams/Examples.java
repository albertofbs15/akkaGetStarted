package com.streams;

import akka.NotUsed;
import akka.actor.ActorSystem;
import akka.stream.ActorMaterializer;
import akka.stream.Materializer;
import akka.stream.javadsl.Keep;
import akka.stream.javadsl.RunnableGraph;
import akka.stream.javadsl.Sink;
import akka.stream.javadsl.Source;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;

/**
 * Created by AHernandezS on 21/06/2017.
 */
public class Examples {

    final ActorSystem system = ActorSystem.create("reactive-tweets");
    final Materializer mat = ActorMaterializer.create(system);

    public void example1() {
        final Source<Integer, NotUsed> source =
                Source.from(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));

        // note that the Future is scala.concurrent.Future
        final Sink<Integer, CompletionStage<Integer>> sink =
                Sink.<Integer, Integer> fold(0, (aggr, next) -> aggr + next);

        // connect the Source to the Sink, obtaining a RunnableFlow
        final RunnableGraph<CompletionStage<Integer>> runnable =
                source.toMat(sink, Keep.right());

        // materialize the flow
        final CompletionStage<Integer> sum = runnable.run(mat);

        sum.thenAccept(result -> {System.out.println(result);});

        sum.thenRun(() -> system.terminate());
    }

    public void example2() {
        final Source<Integer, NotUsed> source =
                Source.from(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
        final Sink<Integer, CompletionStage<Integer>> sink =
                Sink.<Integer, Integer> fold(0, (aggr, next) -> aggr + next);
        final CompletionStage<Integer> sum = source.runWith(sink, mat);

        sum.thenAccept(result -> {System.out.println(result);});
        sum.thenRun(() -> system.terminate());
    }

    public void example3() {
        final Source<Integer, NotUsed> source =
                Source.from(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));

        source.map(x -> 0); // has no effect on source, since it's immutable
        CompletionStage<Integer> value = source.runWith(Sink.fold(0, (agg, next) -> agg + next), mat); // 55
        value.thenAccept(result ->  System.out.println(result));


        final Source<Integer, NotUsed> zeroes = source.map(x -> 0);// returns new Source<Integer>, with `map()` appended
        final Sink<Integer, CompletionStage<Integer>> fold =
                Sink.<Integer, Integer> fold(0, (agg, next) -> agg + next);
        value = zeroes.runWith(fold, mat); // 0
        value.thenAccept(result ->  System.out.println(result));

    }


    public static void main (String ... args) {
        new Examples().example3();
    }
}
