package com.FSM;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.javadsl.TestKit;
import junit.framework.TestCase;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.LinkedList;

/**
 * Created by AHernandezS on 29/06/2017.
 */
public class BuncherTest extends TestCase {

    static ActorSystem system = ActorSystem.create("BuncherTest");

    @BeforeClass
    public static void setup() {
        system = ActorSystem.create("BuncherTest");
    }

    @AfterClass
    public static void tearDownTest() {
        TestKit.shutdownActorSystem(system);
        system = null;
    }


    @Test
    public void testBuncherActorBatchesCorrectly() {
        new TestKit(system) {{
            final ActorRef buncher =
                    system.actorOf(Props.create(Buncher.class));
            final ActorRef probe = getRef();

            buncher.tell(new SetTarget(probe), probe);
            buncher.tell(new Queue(42), probe);
            buncher.tell(new Queue(43), probe);
            LinkedList<Object> list1 = new LinkedList<>();
            list1.add(42);
            list1.add(43);
            expectMsgEquals(new Batch(list1));
            buncher.tell(new Queue(44), probe);
            buncher.tell(Flush.Flush, probe);
            buncher.tell(new Queue(45), probe);
            LinkedList<Object> list2 = new LinkedList<>();
            list2.add(44);
            expectMsgEquals(new Batch(list2));
            LinkedList<Object> list3 = new LinkedList<>();
            list3.add(45);
            expectMsgEquals(new Batch(list3));
            system.stop(buncher);
        }};
    }

    @Test
    public void testBuncherActorDoesntBatchUninitialized() {
        new TestKit(system) {{
            final ActorRef buncher =
                    system.actorOf(Props.create(Buncher.class));
            final ActorRef probe = getRef();

            buncher.tell(new Queue(42), probe);
            expectNoMsg();
            system.stop(buncher);
        }};
    }
}