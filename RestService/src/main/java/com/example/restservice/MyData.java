package com.example.restservice;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MyData {
    public static void info() throws UnknownHostException {
        System.out.println("Jakub Kowalczyk, 259096");
        System.out.println("Bartosz Kalisz, 259206");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM, hh:MM:ss");
        String date = LocalDateTime.now().format(formatter);
        System.out.println(date);
        System.out.println(System.getProperty("java.version"));
        System.out.println(System.getProperty("user.name"));
        System.out.println(System.getProperty("os.name"));
        System.out.println(Inet4Address.getLocalHost().getHostAddress());
    }
}
