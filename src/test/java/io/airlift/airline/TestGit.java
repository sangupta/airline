package io.airlift.airline;

import org.testng.annotations.Test;

import io.airlift.airline.guava.GuavaUtil;

public class TestGit
{
    @Test
    public void test()
    {
        // simple command parsing example
        git("add", "-p", "file");
        git("remote", "add", "origin", "git@github.com:airlift/airline.git");
        git("-v", "remote", "show", "origin");

        // show help
        git();
        git("help");
        git("help", "git");
        git("help", "add");
        git("help", "remote");
        git("help", "remote", "show");
    }

    private void git(String... args)
    {
        System.out.println("$ git " + GuavaUtil.join(" ", args));
        Git.main(args);
        System.out.println();
    }
}
