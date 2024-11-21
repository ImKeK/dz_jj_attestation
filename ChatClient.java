package org.example;

import java.io.*;
import java.net.*;

public class ChatClient {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 12345;

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT)) {
            new ReadThread(socket).start();
            new WriteThread(socket).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ReadThread extends Thread {
        private BufferedReader in;

        public ReadThread(Socket socket) {
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void run() {
            String message;
            try {
                while ((message = in.readLine()) != null) {
                    System.out.println(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static class WriteThread extends Thread {
        private PrintWriter out;
        private BufferedReader stdIn;

        public WriteThread(Socket socket) {
            try {
                out = new PrintWriter(socket.getOutputStream(), true);
                stdIn = new BufferedReader(new InputStreamReader(System.in));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void run() {
            String message;
            try {
                while ((message = stdIn.readLine()) != null) {
                    out.println(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
