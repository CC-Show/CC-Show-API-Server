package com.boxfox.util.preference;

public class Config {
    private Config defaultInstance;

    private Config() {
    }

    private static class DefaultConfig {
        private static Config instance = new Config();
    }

    public static Config getDefaultInstance() {
        return DefaultConfig.instance;
    }

    public String getString(String propName) {
        return null;
    }
}
