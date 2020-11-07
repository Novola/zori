package club.novola.zori.mixin.accessors;

import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.util.Session;
import net.minecraft.util.Timer;

public interface IMinecraft {
    Timer getTimer();

    void setSession(final Session p0);

    Session getSession();

    void setRightClickDelayTimer(final int p0);

    void clickMouse();

    ServerData getCurrentServerData();
}
