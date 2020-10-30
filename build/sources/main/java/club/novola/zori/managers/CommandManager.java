package club.novola.zori.managers;

import club.novola.zori.command.commands.StatusCommand;
import club.novola.zori.command.commands.ToggleCommand;
import com.google.common.collect.Lists;
import club.novola.zori.Zori;
import club.novola.zori.command.Command;
import club.novola.zori.util.Wrapper;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;

public class CommandManager {
    private final List<Command> commands;

    public CommandManager(){
        commands = Lists.newArrayList(
                new ToggleCommand(),
                new StatusCommand()
				// register commands here
        );

        //for(Module m : Zori.getInstance().moduleManager.getModules()){
        //    commands.add(new ModuleCommand(m));
        //}

        MinecraftForge.EVENT_BUS.register(this);
    }

    public List<Command> getCommands() {
        return commands;
    }

    private void callCommand(String text){
        if(!text.contains(" ")) text += " ";
        String[] split = text.split(" ");
        for(Command c : commands){
            for(String s : c.getAliases()){
                if(s.equalsIgnoreCase(split[0])){
                    try {
                        c.exec(text.substring(split[0].length() + 1));
                    } catch(Exception e){
                        e.printStackTrace();
                        Command.sendErrorMessage("ERR: " + e.toString(), false);
                    }
                    return;
                }
            }
        }
        Command.sendErrorMessage("Unknown command: " + split[0], false);
    }

    @SubscribeEvent
    public void onClientChat(ClientChatEvent event){
        String prefix = Zori.getInstance().clientSettings.getPrefix();
        if(event.getMessage().startsWith(prefix)){
            event.setCanceled(true);
            Wrapper.mc.ingameGUI.getChatGUI().addToSentMessages(event.getMessage());

            if(event.getMessage().equalsIgnoreCase(prefix)) callCommand("help");
            else callCommand(event.getMessage().substring(prefix.length()));
        }
    }
}
