package net.estinet.ClioteSky.network;

/*
Copyright 2016 EstiNet

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;

import net.estinet.ClioteSky.Category;
import net.estinet.ClioteSky.Cliote;
import net.estinet.ClioteSky.ClioteSky;
import net.estinet.ClioteSky.network.protocol.Decosion;

public class ClioteSocket extends Thread {
    private Socket socket;
    private boolean recievedOutput = true;

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public ClioteSocket(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        final boolean[] closes = {true};
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean cont = true;
                while(cont) {
                    cont = false;
                    try {
                        Thread.sleep(30000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (!socket.isClosed()) {
                        if (!recievedOutput) {
                            for (int i = 0; i < ClioteSky.categories.size(); i++) {
                                for (int iter = 0; iter < ClioteSky.categories.get(i).getCliotes().size(); iter++) {
                                    if (ClioteSky.categories.get(i).getCliotes().get(iter).getIP().equals(NetworkUtil.getIP(socket)) && ClioteSky.categories.get(i).getCliotes().get(iter).getPort().equals(Integer.toString(socket.getPort()))) {
                                        ClioteSky.categories.get(i).getCliotes().get(iter).setIsOnline(false);
                                        try {
                                            socket.close();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }
                            ClioteSky.printSignal("Cliote not responding! Connection closed with " + NetworkUtil.getIP(socket) + ":" + socket.getPort());
                            ClioteSky.getConnections().remove(this);
                            closes[0] = false;
                        } else {
                            recievedOutput = false;
                            cont = true;
                        }
                    }
                }
            }
        }).start();
        int close = 0;
        BufferedReader in;
        while (closes[0]) {
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String inputLine = in.readLine();
                if (inputLine == null) {
                    close++;
                }
                if (close > 1) { //REWRITE CLOSE PROTOCOL
                    for (int i = 0; i < ClioteSky.categories.size(); i++) {
                        for (int iter = 0; iter < ClioteSky.categories.get(i).getCliotes().size(); iter++) {
                            if (ClioteSky.categories.get(i).getCliotes().get(iter).getIP().equals(NetworkUtil.getIP(socket)) && ClioteSky.categories.get(i).getCliotes().get(iter).getPort().equals(Integer.toString(socket.getPort()))) {
                                ClioteSky.categories.get(i).getCliotes().get(iter).setIsOnline(false);
                                socket.close();
                            }
                        }
                    }
                    ClioteSky.printSignal("Connection closed with " + NetworkUtil.getIP(socket) + ":" + socket.getPort());
                    ClioteSky.getConnections().remove(this);
                    break;
                } else {
                    Decosion de = new Decosion();
                    recievedOutput = true;
                    if (!inputLine.equalsIgnoreCase("alive")) {
                        ClioteSky.printSignal("Signal recieved from " + NetworkUtil.getIP(socket) + ":" + socket.getPort() + " with query " + inputLine);
                    } else if (ClioteSky.debug) {
                        ClioteSky.printSignal("Signal recieved from " + NetworkUtil.getIP(socket) + ":" + socket.getPort() + " with query " + inputLine);
                    }
                    boolean done = false;
                    String actual = inputLine;//EncryptionUtil.decrypt(inputLine.getBytes(), ClioteSky.privatekey);
                    for (Category category : ClioteSky.categories) {
                        for (Cliote cliote : category.getCliotes()) {
                            if (cliote.getIP().equals(NetworkUtil.getIP(socket)) && cliote.getPort().equals(Integer.toString(socket.getPort()))) {
                                de.decode(actual, cliote);
                                done = true;
                                break;
                            }
                        }
                    }
                    if (!done) {
                        de.decode(actual, new Cliote("unknown", NetworkUtil.getIP(socket), Integer.toString(socket.getPort())));
                    }
                }
            } catch (SocketException e) {
                for (int i = 0; i < ClioteSky.categories.size(); i++) {
                    for (int iter = 0; iter < ClioteSky.categories.get(i).getCliotes().size(); iter++) {
                        if (ClioteSky.categories.get(i).getCliotes().get(iter).getIP().equals(NetworkUtil.getIP(socket)) && ClioteSky.categories.get(i).getCliotes().get(iter).getPort().equals(Integer.toString(socket.getPort()))) {
                            ClioteSky.categories.get(i).getCliotes().get(iter).setIsOnline(false);
                        }
                    }
                }
                ClioteSky.printSignal("Connection closed with " + NetworkUtil.getIP(socket) + ":" + socket.getPort());
                ClioteSky.getConnections().remove(this);
                break;
            } catch (ArrayIndexOutOfBoundsException e) {
                ClioteSky.printSignal("Connection closed with " + NetworkUtil.getIP(socket) + ":" + socket.getPort());
                closes[0] = false;
            } catch (Exception e) {
                ClioteSky.fprintln("Oops! Connection exception. :/");
                e.printStackTrace();
            }
        }
    }
}
