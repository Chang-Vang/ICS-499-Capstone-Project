package edu.metrostate.migrations;

import edu.metrostate.Util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class Migration implements Comparable<Migration> {

    private String name;
    private List<String> lines;

    public Migration(String name, List<String> lines) {
        this.name = name;
        this.lines = lines;
    }

    public Integer order() {
        String[] parts = name.split("-");
        try {
            return Integer.parseInt(parts[0]);
        } catch (Exception ex) {
            return Integer.valueOf(-1);
        }
    }

    public void run(Connection connection) {
        if (migrationRun(connection)) {
            return;
        }
        Statement sqlStatement = null;
        String statement = "";
        try {
            System.out.println("Performing Migration " + name);
            sqlStatement = connection.createStatement();
            for (String line: lines) {
                statement += line;
                if (line.contains(";")) {
                    sqlStatement.execute(statement);
                    statement = "";
                }
            }
            if (!statement.isEmpty()) {
                sqlStatement.execute(statement);
            }
            sqlStatement.execute("INSERT INTO MIGRATIONS (name) values ('" + name + "')");
            sqlStatement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            Util.closeQuietly(sqlStatement);
        }
    }

    private Boolean migrationRun(Connection connection) {
        ResultSet resultSet = null;
        try {
            Statement migrationCheck = connection.createStatement();
            String query = "SELECT * FROM migrations WHERE name='" + this.name + "';";
            resultSet = migrationCheck.executeQuery(query);
            Boolean run = resultSet.next();
            return run;
        } catch (SQLException ex) {
            return false;
        } finally {
            Util.closeQuietly(resultSet);
        }
    }

    @Override
    public int compareTo(Migration o) {
        return this.order() - o.order();
    }
}
