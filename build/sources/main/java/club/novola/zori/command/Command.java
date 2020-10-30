package club.novola.zori.command;

import com.mojang.realmsclient.gui.ChatFormatting;
import club.novola.zori.util.Wrapper;
import net.minecraft.util.text.TextComponentString;

public abstract class Command {
    private final String[] aliases; // list of valid aliases e.g. new String[](){"toggle", "t", "bruh"}
    private final String syntax; // used for printing usage in chat

    public Command(String[] aliases, String syntax){
        this.aliases = aliases;
        this.syntax = syntax;
    }

	// args = anyt text after the command itself, use args.split(" ") to get an array of args split by a space
    public abstract void exec(String args) throws Exception;

    public String[] getAliases() {
        return aliases;
    }

    public String getSyntax() {
        return syntax;
    }

    public static void sendClientMessage(String message, boolean forcePermanent){
        if(Wrapper.getPlayer() == null) return;
        try{
            TextComponentString component = new TextComponentString(ChatFormatting.AQUA + "<Zori.club> " + ChatFormatting.RED + message);
            int i = forcePermanent ? 0 : 12076;
            Wrapper.mc.ingameGUI.getChatGUI().printChatMessageWithOptionalDeletion(component, i);
        } catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    public static void sendErrorMessage(String message, boolean forcePermanent){
        if(Wrapper.getPlayer() == null) return;
        try{
            TextComponentString component = new TextComponentString(ChatFormatting.DARK_RED + "<Zori.club> " + ChatFormatting.RED + message);
            int i = forcePermanent ? 0 : 12076;
            Wrapper.mc.ingameGUI.getChatGUI().printChatMessageWithOptionalDeletion(component, i);
        } catch (NullPointerException e){
            e.printStackTrace();
        }
    }
}
