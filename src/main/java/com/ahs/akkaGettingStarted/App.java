package com.ahs.akkaGettingStarted;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

/**
 * Hello world!
 *
 */
public class App extends AbstractActor {

    public static ActorSystem system = ActorSystem.create("ActorSystem");


    public static void main( String[] args ) {
        ActorRef firstRef = system.actorOf(Props.create(App.class), "first-actor");
        System.out.println("First : " + firstRef);
        firstRef.tell("printit", ActorRef.noSender());

        ActorRef trhidRef = system.actorOf(Props.create(App.class), "tercero-actor");
        System.out.println("Tercero : " + trhidRef);
        trhidRef.tell("printit", trhidRef.noSender());
    }

    public Receive createReceive() {
        return receiveBuilder()
                .matchEquals("printit", p -> {
                    ActorRef secondRef = getContext().actorOf(Props.empty(), "second-actor");
                    System.out.println("second: " +  secondRef);
                })
                .build();
    }
}
