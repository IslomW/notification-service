package org.sharom.notificationservice.contex;

public class LangContext {

    private static final ThreadLocal<String> currentLang = new ThreadLocal<>();

    public static void setLang(String lang) {
        currentLang.set(lang);
    }

    public static String getLang() {
        return currentLang.get();
    }

    public static void clear() {
        currentLang.remove();
    }
}