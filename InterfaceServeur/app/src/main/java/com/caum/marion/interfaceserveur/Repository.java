package com.caum.marion.interfaceserveur;

import java.util.ArrayList;

public class Repository {

    public static ArrayList<Table> tables;
    public static String tableSelectionnee;
    public static String platAModifier;

    static {
        tables = new ArrayList<Table>();
        tableSelectionnee = "";
        platAModifier = "";
    }
}
