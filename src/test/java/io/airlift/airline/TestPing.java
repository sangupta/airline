package io.airlift.airline;

import org.testng.annotations.Test;

import io.airlift.airline.guava.GuavaUtil;

public class TestPing
{
    @Test
    public void test()
    {
        // simple command parsing example
        ping();
        ping("-c", "5");
        ping("--count", "9");

        // show help
        ping("-h");
        ping("--help");
    }

    private void ping(String... args)
    {
        System.out.println("$ ping " + GuavaUtil.join(" ", args));
        Ping.main(args);
        System.out.println();
    }
}
