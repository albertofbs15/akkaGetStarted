package com.ahs.Supervising;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

/**
 * Created by AHernandezS on 12/06/2017.
 */
public class SupervisingActor extends AbstractActor{

    public static ActorSystem actorSystem = ActorSystem.create();
    ActorRef child;

    public static void main (String[] args) {
        ActorRef actorRef = actorSystem.actorOf(Props.create(SupervisingActor.class));
        actorRef.tell("failChild", ActorRef.noSender());
    }

    @Override
    public void preStart() throws Exception {
        child = getContext().actorOf(Props.create(SupervisedActor.class));
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .matchEquals("failChild" , p -> {
                    child.tell("fail", getSelf());
                })
                .build();
    }
}
