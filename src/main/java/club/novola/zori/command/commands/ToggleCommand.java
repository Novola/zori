package club.novola.zori.command.commands;

import com.mojang.realmsclient.gui.ChatFormatting;
import joptsimple.internal.Strings;
import club.novola.zori.Zori;
import club.novola.zori.command.Command;
import club.novola.zori.module.Module;

// simple command with only one argument, in this case the argument can contain spaces
public class ToggleCommand extends Command {
    public ToggleCommand() {
        super(new String[]{"toggle", "t"}, "toggle <Module>");
    }

    @Override
    public void exec(String args) throws Exception {
        if(Strings.isNullOrEmpty(args)){
            sendErrorMessage("Module expected", false);
            return;
        }

        Module m = Zori.getInstance().moduleManager.getModuleByName(args);
        if(m == null){
            sendErrorMessage("Unknown module: " + args, false);
            return;
        }

        sendClientMessage(m.getName() + (m.toggle() ? ChatFormatting.GREEN + " enabled" : ChatFormatting.RED + " disabled"), false);
    }
}
