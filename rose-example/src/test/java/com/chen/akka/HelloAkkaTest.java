package com.chen.akka;

import scala.concurrent.duration.Duration;
import akka.actor.*;
import akka.testkit.JavaTestKit;
import akka.testkit.TestActorRef;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;


public class HelloAkkaTest {

    static ActorSystem system;

    @BeforeClass
    public static void setup() {
        system = ActorSystem.create();
    }

    @AfterClass
    public static void teardown() {
        system.shutdown();
        system.awaitTermination(Duration.create("10 seconds"));
    }

    @Test
    public void testSetGreeter() {
        new JavaTestKit(system) {{
            final TestActorRef<HelloAkkaJava.Greeter> greeter =
                    TestActorRef.create(system, Props.create(HelloAkkaJava.Greeter.class), "greeter1");

            greeter.tell(new HelloAkkaJava.WhoToGreet("testkit"), getTestActor());

            Assert.assertEquals("hello, testkit", greeter.underlyingActor().greeting);
        }};
    }

    @Test
    public void testGetGreeter() {
        new JavaTestKit(system) {{

            final ActorRef greeter = system.actorOf(Props.create(HelloAkkaJava.Greeter.class), "greeter2");

            greeter.tell(new HelloAkkaJava.WhoToGreet("testkit"), getTestActor());
            greeter.tell(new HelloAkkaJava.Greet(), getTestActor());

            final HelloAkkaJava.Greeting greeting = expectMsgClass(HelloAkkaJava.Greeting.class);

            new Within(duration("10 seconds")) {
                protected void run() {
                    Assert.assertEquals("hello, testkit", greeting.message);
                }
            };
        }};
    }
}

