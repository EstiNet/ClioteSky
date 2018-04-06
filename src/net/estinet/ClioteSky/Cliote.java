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

import java.io.Serializable;

import net.estinet.ClioteSky.network.ClioteSocket;

public class Cliote implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -1613981352507093670L;
    /*
     * Connection Object
     */
    private String name;
    private String ip;
    private String port;
    private String password = "";
    private boolean isonline = false;

    public Cliote(String connectionname, String connectip, String conport) {
        name = connectionname;
        ip = connectip;
        port = conport;
    }

    public Cliote(String connectionname, String connectip, String conport, String password) {
        name = connectionname;
        ip = connectip;
        port = conport;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getIP() {
        return ip;
    }

    public String getPort() {
        return port;
    }

    public String getPassword() {
        return password;
    }

    public boolean getIsOnline() {
        return isonline;
    }

    public void setName(String newname) {
        name = newname;
    }

    public void setIP(String newip) {
        ip = newip;
    }

    public void setPort(String newport) {
        port = newport;
    }

    public void setIsOnline(boolean isonstate) {
        isonline = isonstate;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
