package com.komy;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigManager {
    private static final Properties properties = new Properties();
    private static final boolean isLocal;

    static {
        String ci = System.getenv("CI");
        isLocal = (ci == null || !ci.equalsIgnoreCase("true"));

        if (isLocal) {
            try {
                FileInputStream fis = new FileInputStream("config.properties");
                properties.load(fis);
            } catch (IOException e) {
                throw new RuntimeException("Could not load config.properties", e);
            }
        }
    }

    public static String get(String key) {
        if (isLocal) {
            return properties.getProperty(key);
        } else {
            return System.getenv(key);
        }
    }
}


