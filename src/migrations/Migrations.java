package edu.metrostate.migrations;

import edu.metrostate.Util;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class Migrations {
    public void runMigrations(Connection connection) {
        try {
            createMigrationTable(connection);
            List<Migration> migrations = loadMigrations();
            migrations.forEach(migration -> migration.run(connection));
        } catch (Exception ex) {
            // no op
            ex.printStackTrace();
        }
    }

    private List<Migration> loadMigrations() throws IOException {
        String path = getPath();
        File dir = new File(path);
        List<File> sqlFiles = new ArrayList<>();
        if (dir.isDirectory()) {
            Files.walk(dir.toPath()).forEach(new Consumer<Path>() {
                @Override
                public void accept(Path path) {
                    if (path.toString().endsWith(".sql")) {
                        sqlFiles.add(path.toFile());
                    }
                }
            });
        }
        List<Migration> migrations = sqlFiles
                .stream()
                .map(file -> {
                    try {
                        List<String> lines = Files.readAllLines(file.toPath());
                        return new Migration(file.getName(), lines);
                    } catch (Exception ex) {
                        return null;
                    }
                })
                .filter(migration -> migration != null)
                .sorted()
                .collect(Collectors.toList());
        return migrations;
    }

    private String getPath()
    {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        URL url = loader.getResource("migrations");

        String path = url.getPath();
        String pathMod1 = path.replace("libs/App.jar!/migrations/", "resources/main/migrations/");
        String pathMod2 = pathMod1.replace("/", "\\");

        return pathMod2.replace("file:\\\\", "");
    }

    private void createMigrationTable(Connection connection) {
        Statement statement = null;
        try {
            statement = connection.createStatement();
            String migrationTable = "CREATE TABLE IF NOT EXISTS migrations (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT);";
            statement.execute(migrationTable);
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            Util.closeQuietly(statement);
        }
    }
}
