package io.github.dagezi.vbw;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class Cmd {
    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger("Vbw");
        BeWinding beWinding = new BeWinding(logger);
        beWinding.handle(new File(args[0]), new File(args[1]));
    }
}
