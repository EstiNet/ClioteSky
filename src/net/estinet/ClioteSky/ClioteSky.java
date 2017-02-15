package net.estinet.ClioteSky;

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

import java.io.IOException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import jline.console.CursorBuffer;
import net.estinet.ClioteSky.network.ClioteSocket;
import net.estinet.ClioteSky.network.NetworkUtil;
import net.estinet.ClioteSky.network.protocol.InputPacket;
import net.estinet.ClioteSky.network.protocol.OutputPacket;
import jline.console.ConsoleReader;

public class ClioteSky {
    public static String version = "1.2.0";
    public static State state = State.ENABLING;
    public static boolean exit = true;
    public static boolean debug = false;
    public static long commandid = 0;
    public static int port = 36000;
    public static PublicKey publickey = null;
    public static PrivateKey privatekey = null;
    public static ConsoleReader console;

    private static CursorBuffer stashed = null;

    static {
        try {
            console = new ConsoleReader();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<ClioteSocket> connections = new CopyOnWriteArrayList<>();

    public static List<Command> commands = new ArrayList<>();
    public static List<Category> categories = new CopyOnWriteArrayList<>();

    public static List<InputPacket> inputPackets = new ArrayList<>();
    public static List<OutputPacket> outputPackets = new ArrayList<>();

    public static List<ClioteSocket> getConnections() {
        return connections;
    }

    public static void setConnections(List<ClioteSocket> sockets) {
        connections = sockets;
    }

    public static void addClioteSocket(ClioteSocket socket) {
        connections.add(socket);
    }

    public static Cliote getCliote(String clioteName) {
        for (Category category : categories) {
            for (Cliote cliote : category.getCliotes()) {
                if (cliote.getName().equals(clioteName)) {
                    return cliote;
                }
            }
        }
        return null;
    }

    public static void setCliote(Cliote cliote) {
        for (int it = 0; it < categories.size(); it++) {
            for (int i = 0; i < categories.get(it).getCliotes().size(); i++) {
                if (categories.get(it).getCliotes().get(i).getName().equals(cliote.getName())) {
                    List<Cliote> cliotes = categories.get(it).getCliotes();
                    cliotes.set(i, cliote);
                    categories.get(it).setCliotes(cliotes);
                }
            }
        }
    }

    public static ClioteSocket getClioteSocket(Cliote cliote) {
        for (ClioteSocket cs : connections) {
            if (NetworkUtil.getIP(cs.getSocket()).equals(cliote.getIP()) && cs.getSocket().getPort() == Integer.parseInt(cliote.getPort())) {
                return cs;
            }
        }
        return null;
    }

    public static Category getClioteCategory(Cliote cliote) {
        for (Category category : categories) {
            for (Cliote cliot : category.getCliotes()) {
                if (cliot.getName().equals(cliote.getName())) {
                    return category;
                }
            }
        }
        return null;
    }

    public static void println(String output) {
        stashLine();
        System.out.println("[System]: " + output);
        unstashLine();
    }

    public static void printSignal(String output) {
        stashLine();
        System.out.println("[TCP]: " + output);
        unstashLine();
    }

    public static String getPublicKey() {
        String pub = "";
        for (byte b : ClioteSky.publickey.getEncoded()) {
            pub += b;
        }
        return pub;
    }

    public static String getPrivateKey() {
        String pri = "";
        for (byte b : ClioteSky.privatekey.getEncoded()) {
            pri += b;
        }
        return pri;
    }

    /*
     * Prints to console without any prefix.
     */
    public static void fprintln(String output) {
        stashLine();
        System.out.println(output);
        unstashLine();
    }

    public static void stashLine() {
        stashed = console.getCursorBuffer().copy();
        try {
            console.getOutput().write("\u001b[1G\u001b[K");
            console.flush();
        } catch (IOException e) {
            // ignore
        }
    }

    public static void unstashLine() {
        try {
            console.resetPromptLine(console.getPrompt(), stashed.toString(), stashed.cursor);
        } catch (IOException e) {
            // ignore
        }
    }
}
