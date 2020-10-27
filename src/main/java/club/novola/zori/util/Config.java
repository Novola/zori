package club.novola.zori.util;

import club.novola.zori.Zori;
import club.novola.zori.hud.HudComponent;
import club.novola.zori.module.Module;
import club.novola.zori.setting.Setting;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.io.*;

public class Config {
    public Config(){
        file = new File(Minecraft.getMinecraft().mcDataDir.getAbsolutePath(), "ZoriConfig.txt");
    }

    private File file;

    public void save() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));

        Zori.getInstance().moduleManager.getModules().forEach((name, module) -> {
            try {
                writer.write("MODULE = " + name + "\r\n");
                writer.write("ENABLED = " + module.isEnabled() + "\r\n");
                writer.write("BIND = " + Keyboard.getKeyName(module.getBind()) + "\r\n");
                for (Setting setting : Zori.getInstance().settingManager.getSettingsForMod(module)) {
                    writer.write("SETTING = " + setting.getName() + "\r\n");
                    writer.write("SETTING VALUE = " + (setting.getValue() instanceof Color ? ((Color) setting.getValue()).getRGB() : setting.getValue()) + "\r\n");
                }
            } catch (IOException ignored){}
            if(module.isEnabled()) module.disable(); // call onDisable() if the module is enabled
        });

        for(HudComponent component : Zori.getInstance().hudComponentManager.getComponents()){
            writer.write("HUD COMPONENT = " + component.getName() + "\r\n");
            writer.write("COMPONENT X = " + component.getX() + "\r\n");
            writer.write("COMPONENT Y = " + component.getY() + "\r\n");
        }

        for(String friend : Zori.getInstance().playerStatus.getFriends()){
            writer.write("FRIEND = " + friend + "\r\n");
        }
        for(String enemy : Zori.getInstance().playerStatus.getEnemies()){
            writer.write("ENEMY = " + enemy + "\r\n");
        }

        writer.close();
    }

    public void load() throws IOException {
        FileInputStream fstream = new FileInputStream(file.getAbsolutePath());
        DataInputStream in = new DataInputStream(fstream);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));

        Module parsingModule = null;
        Setting parsingSetting = null;
        HudComponent parsingComponent = null;

        String line;
        while((line = br.readLine()) != null) {
            String[] split = line.split(" = ");
            switch(split[0]){
                case "MODULE":
                    parsingModule = Zori.getInstance().moduleManager.getModuleByName(split[1]);
                    break;
                case "ENABLED":
                    if(parsingModule != null && Boolean.parseBoolean(split[1])) parsingModule.enable();
                    break;
                case "BIND":
                    if(parsingModule != null) parsingModule.setBind(Keyboard.getKeyIndex(split[1]));
                    break;
                case "SETTING":
                    if(parsingModule != null)
                        parsingSetting = Zori.getInstance().settingManager.getSetting(split[1], parsingModule);
                    break;
                case "SETTING VALUE":
                    if(parsingModule != null && parsingSetting != null){
                        Object value = parseSettingValue(parsingSetting, split[1]);
                        if(value != null) parsingSetting.setValue(value);
                    }
                    break;
                case "HUD COMPONENT":
                    parsingComponent = Zori.getInstance().hudComponentManager.getComponentByName(split[1]);
                    break;
                case "COMPONENT X":
                    if(parsingComponent != null) parsingComponent.setX(Integer.parseInt(split[1]));
                    break;
                case "COMPONENT Y":
                    if(parsingComponent != null) parsingComponent.setY(Integer.parseInt(split[1]));
                    break;
                case "FRIEND":
                    if(Zori.getInstance().playerStatus.getStatus(split[1]) != 1) Zori.getInstance().playerStatus.addFriend(split[1]);
                    break;
                case "ENEMY":
                    if(Zori.getInstance().playerStatus.getStatus(split[1]) != -1) Zori.getInstance().playerStatus.addEnemy(split[1]);
                    break;
            }
        }
    }

    private Object parseSettingValue(Setting setting, String text){
        Object value = setting.getValue();
        if(value instanceof Integer) return Integer.parseInt(text);
        if(value instanceof Double) return Double.parseDouble(text);
        if(value instanceof Float) return Float.parseFloat(text);
        if(value instanceof Boolean) return Boolean.parseBoolean(text);
        if(value instanceof Color) return new Color(Integer.parseInt(text));
        if(value instanceof Enum){
            try {
                Class<Enum> e = ((Enum)value).getDeclaringClass();
                return Enum.valueOf(e, text);
            } catch(IllegalArgumentException ignored){}
        }
        return null;
    }
}
