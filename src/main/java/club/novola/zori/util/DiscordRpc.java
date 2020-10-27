package club.novola.zori.util;

import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRPC;
import club.minnced.discord.rpc.DiscordRichPresence;
import club.novola.zori.Zori;
import club.novola.zori.util.Wrapper;

// https://github.com/MinnDevelopment/java-discord-rpc/blob/master/examples/java/ml/yuuki/chris/rpc/examples/WindowTest.java
public class DiscordRpc {
    public DiscordRpc(){
        INSTANCE = this;
        lib = DiscordRPC.INSTANCE;
        presence = new DiscordRichPresence();
        handlers = new DiscordEventHandlers();
    }

    private Thread thread = null;
    public String state = "";
    public static DiscordRpc INSTANCE;

    private DiscordRPC lib;
    private DiscordRichPresence presence;
    private DiscordEventHandlers handlers;

    private void init(){
        lib.Discord_Initialize("770454455627022366" /* discord app id */, handlers, true, "");

        presence.startTimestamp = System.currentTimeMillis() / 1000;
        presence.details = "";
        presence.state = state;
<<<<<<< HEAD
        presence.largeImageKey = "zori"; // image names you uploaded to the discord dev thing
        presence.largeImageText = "Zori v0.0.1-beta";
=======
        presence.largeImageKey = "img"; // image names you uploaded to the discord dev thing
		presence.smallImageKey = "zori"; // image names you uploaded to the discord dev thing
		presence.smallImageText = "Zori v0.0.1-BETA"; // text that shows when you hover over the small image
>>>>>>> 0a9a231cace85615a0f47c6b18dd8ba0fcd4be23
        lib.Discord_UpdatePresence(presence);
    }

    public void start(){
        init();

        thread = new Thread(() -> {
            while(!Thread.currentThread().isInterrupted()) {
                lib.Discord_RunCallbacks();
                if (!presence.state.equals(state)) {
                    presence.state = state;
                    lib.Discord_UpdatePresence(presence);
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    break;
                }
            }
        }, "RPC-Callback-Handler");

        thread.start();
        Zori.getInstance().log.info("Started Discord RPC");
    }

    public void stop(){
        if(thread != null && thread.isAlive() && !thread.isInterrupted()) {
            thread.interrupt();
            lib.Discord_Shutdown();
            thread = null;
            Zori.getInstance().log.info("Stopped Discord RPC");
        }
    }
}
