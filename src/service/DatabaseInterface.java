package edu.metrostate.service;

import java.util.ArrayList;

public interface DatabaseInterface {
    public static DatabaseInterface getInstance() { return null; }
    public default void openConnection() {}

    public default void migrate() { }
    public default void list() {}
    public default ArrayList<String> getCategories() { return null;  }

}
