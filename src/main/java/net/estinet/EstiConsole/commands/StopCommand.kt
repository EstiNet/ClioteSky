package net.estinet.EstiConsole.commands

import net.estinet.EstiConsole.ConsoleCommand
import net.estinet.EstiConsole.disable
import java.util.*

class StopCommand : ConsoleCommand() {
    init {
        super.cName = "stop"
        super.desc = "Stops the java process (not the console). Turns off auto-start."
    }
    override fun run(args: ArrayList<String>){
        disable()
    }
}

