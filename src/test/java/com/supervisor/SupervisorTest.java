package com.supervisor;

import akka.actor.ActorSystem;
import akka.testkit.*;
import junit.framework.TestCase;
import jdocs.AbstractJavaTest;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import scala.concurrent.duration.Duration;
import static java.util.concurrent.TimeUnit.SECONDS;
import static akka.japi.Util.immutableSeq;
import scala.concurrent.Await;

/**
 * Created by AHernandezS on 16/06/2017.
 */
public class SupervisorTest extends TestCase {

    static ActorSystem system;
    Duration timeout = Duration.create(5, SECONDS);

    @BeforeClass
    public static void start() {
        system = ActorSystem.create("FaultHandlingTest", config);
    }

    @AfterClass
    public static void cleanup() {
        TestKit.shutdownActorSystem(system);
        system = null;
    }

    @Test
    public void mustEmploySupervisorStrategy() throws Exception {
        // code here
    }
}