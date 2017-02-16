package net.estinet.ClioteSky.commands;

import net.estinet.ClioteSky.ClioteSky;
import net.estinet.ClioteSky.Command;

import java.util.List;

public class Version extends Command {
    public Version() {
        super.setName("Version");
        super.setDescription("Shows the system version.");
    }

    @Override
    public void run(List<String> args) {
        ClioteSky.fprintln("ClioteSky Version " + ClioteSky.version);
    }
}
