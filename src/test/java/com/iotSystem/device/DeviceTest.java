package com.iotSystem.device;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.testkit.javadsl.TestKit;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.Optional;

/**
 * Created by AHernandezS on 12/06/2017.
 */
public class DeviceTest extends TestCase {

    ActorSystem system = ActorSystem.create();

    @Test
    public void testReplyWithEmptyReadingIfNoTemperatureIsKnown() {

        TestKit probe = new TestKit(system);
        ActorRef deviceActor = system.actorOf(Device.props("group", "device"));

        deviceActor.tell(new Device.ReadTemperature(42L), probe.getRef());
        Device.RespondTemperature response = probe.expectMsgClass(Device.RespondTemperature.class);
        assertEquals(42L, response.requestId);
        assertEquals(Optional.empty(), response.value);
    }
}