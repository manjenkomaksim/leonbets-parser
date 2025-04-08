package com.jsparser.service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class LoggerService {
    private final BufferedWriter writer;

    public LoggerService(String filename) {
        try {
            FileWriter fw = new FileWriter(filename, false);
            this.writer = new BufferedWriter(fw);
        } catch (IOException e) {
            throw new RuntimeException("Failed to open log file: " + filename, e);
        }
    }

    public synchronized void log(String message) {
        try {
            System.out.println(message);
            writer.write(message);
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            System.err.println("Logger error: " + e.getMessage());
        }
    }

    public void close() {
        try {
            writer.close();
        } catch (IOException e) {
            System.err.println("Logger close error: " + e.getMessage());
        }
    }
}
