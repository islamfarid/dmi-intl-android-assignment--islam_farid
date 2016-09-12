package com.example;

import java.io.File;
import java.io.IOException;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class Generator {
    public static void main(String[] args) throws Exception {
        Schema schema = makeSchema();

        Entity user = schema.addEntity("BookDetail");
        user.addStringProperty("id").primaryKey();
        user.addStringProperty("title");
        user.addDoubleProperty("price");
        user.addStringProperty("image");
        user.addStringProperty("author");
        user.addStringProperty("link");
        genereteDbSchema(schema, "../islam/books_db/");
    }

    private static Schema makeSchema() {
        return new Schema(2, "com.islam.example.dmitask.db");
    }

    private static void genereteDbSchema(Schema schema, String filePath)
            throws Exception, IOException {
        Util.deleteDirectory(new File(filePath), false);
        new File(filePath).mkdirs();
        new DaoGenerator().generateAll(schema, filePath);
    }

    private static class Util {

        private static void deleteDirectory(File directory, boolean deleteRoot) {
            if (directory.exists()) {
                File[] files = directory.listFiles();
                if (null != files) {
                    for (int i = 0; i < files.length; i++) {
                        if (files[i].isDirectory()) {
                            deleteDirectory(files[i], true);
                        } else {
                            files[i].delete();
                        }
                    }
                }
            }
            if (deleteRoot) {
                directory.delete();
            }
        }
    }
}
