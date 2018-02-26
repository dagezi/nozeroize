package io.github.dagezi.nonzeroize;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class Cmd {
    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(Cmd.class.getName());
        Nonzeroize nonzeroize = new Nonzeroize(logger);
        nonzeroize.handle(new File(args[0]), new File(args[1]));
    }
}
