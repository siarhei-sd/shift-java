package org.example;

import java.util.ArrayList;
import java.util.List;

public class Arguments {
    private static final String ARG_A = "-a";
    private static final String ARG_P = "-p";
    private static final String ARG_O = "-o";
    private static final String ARG_S = "-s";
    private static final String ARG_F = "-f";
    private static final String PATH_NOT_ENTERED = "The path has not been entered!";
    private static final String PREFIX_NOT_ENTERED = "The prefix has not been entered!";

    private String path = "";
    private String prefix = "";

    private final List<String> files = new ArrayList<>();

    private boolean argA = false;
    private boolean argS = false;
    private boolean argF = false;

    public List<String> getFiles() {
        return files;
    }

    public boolean isArgF() {
        return argF;
    }

    public void setArgF(boolean argF) {
        this.argF = argF;
    }

    public boolean isArgS() {
        return argS;
    }

    public void setArgS(boolean argS) {
        this.argS = argS;
    }

    public boolean isArgA() {
        return argA;
    }

    public void setArgA(boolean argA) {
        this.argA = argA;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setListArg(String[] args) {
        cleanArgs();
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case ARG_O:
                    if (i != args.length - 1) {
                        setPath(args[++i]);
                    } else {
                        System.out.println(PATH_NOT_ENTERED);
                    }
                    break;
                case ARG_P:
                    if (i != args.length - 1) {
                        setPrefix(args[++i]);
                    } else {
                        System.out.println(PREFIX_NOT_ENTERED);
                    }
                    break;
                case ARG_A:
                    setArgA(true);
                    break;
                case ARG_S:
                    setArgS(true);
                    break;
                case ARG_F:
                    setArgF(true);
                    break;
                default:
                    files.add(args[i]);
            }
        }
    }

    private void cleanArgs() {
        files.clear();
        setPath("");
        setPrefix("");
        setArgA(false);
        setArgS(false);
        setArgF(false);
    }
}