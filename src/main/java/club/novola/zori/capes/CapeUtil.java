package club.novola.zori.capes;

import club.novola.zori.util.Wrapper;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;

import javax.imageio.ImageIO;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;

public class CapeUtil {

    public static ArrayList<String> lines;

    public static List<ResourceLocation> capeStuff = new ArrayList<ResourceLocation>();

    static {
        try {
            capeStuff.add(Wrapper.mc.getTextureManager().getDynamicTextureLocation("bloodhack/capes", new DynamicTexture(ImageIO.read(new URL("https://imgur.com/dOfrSbD.png")))));
            capeStuff.add(Wrapper.mc.getTextureManager().getDynamicTextureLocation("bloodhack/capes", new DynamicTexture(ImageIO.read(new URL("https://imgur.com/piMsBwO.png")))));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getUsersCape() {
        try {
            final URL url = new URL("https://pastebin.com/raw/H2KzpAJK");
            final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                CapeUtil.lines.add(line);
            }
            bufferedReader.close();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static boolean isCapeUser(final String name) {
        return CapeUtil.lines.contains(name);
    }

    static {
        CapeUtil.lines = new ArrayList<String>();
    }

}
