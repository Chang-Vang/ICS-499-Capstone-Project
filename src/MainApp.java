package edu.metrostate;

import edu.metrostate.service.DatabaseImplementation;

public class MainApp {


    public static void main(String[] args) {
        DatabaseImplementation db = DatabaseImplementation.getInstance();
        db.migrate();


    }
}
