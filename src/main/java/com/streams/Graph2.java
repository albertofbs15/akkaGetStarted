package com.streams;

import akka.NotUsed;
import akka.actor.ActorSystem;
import akka.stream.ActorMaterializer;
import akka.stream.ClosedShape;
import akka.stream.Materializer;
import akka.stream.UniformFanOutShape;

import akka.stream.javadsl.Broadcast;
import akka.stream.javadsl.Flow;
import akka.stream.javadsl.GraphDSL;
import akka.stream.javadsl.Keep;
import akka.stream.javadsl.RunnableGraph;
import akka.stream.javadsl.Sink;
import akka.stream.javadsl.Source;
import javafx.util.Pair;

import java.util.concurrent.CompletionStage;

/**
 * Created by AHernandezS on 22/06/2017.
 */
public class Graph2 {

    public static void main (String ... args) {
        final ActorSystem system = ActorSystem.create();
        final Materializer mat = ActorMaterializer.create(system);

        final Sink<Integer, CompletionStage<Integer>> topHeadSink = Sink.head();
        final Sink<Integer, CompletionStage<Integer>> bottomHeadSink = Sink.head();
        final Flow<Integer, Integer, NotUsed> sharedDoubler = Flow.of(Integer.class).map(elem -> elem * 2);

//        final RunnableGraph<Pair<CompletionStage<Integer>, CompletionStage<Integer>>> g =
//                RunnableGraph.<Pair<CompletionStage<Integer>, CompletionStage<Integer>>>fromGraph(
//                        GraphDSL.create(
//                                topHeadSink, // import this sink into the graph
//                                bottomHeadSink, // and this as well
//                                Keep.both(),
//                                (b, top, bottom) -> {
//                                    final UniformFanOutShape<Integer, Integer> bcast =
//                                            b.add(Broadcast.create(2));
//
//                                    b.from(b.add(Source.single(1))).viaFanOut(bcast)
//                                            .via(b.add(sharedDoubler)).to(top);
//                                    b.from(bcast).via(b.add(sharedDoubler)).to(bottom);
//                                    return ClosedShape.getInstance();
//                                }
//                        )
//                );

//        CompletionStage<List<String>> value = result.run(mat);
//
//        value.thenAccept(list -> list.forEach(System.out::println));
    }
}
