package club.novola.zori.managers;

import club.novola.zori.module.Module;
import club.novola.zori.setting.Setting;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class SettingManager {
    private final List<Setting> settings;

    public SettingManager(){
        settings = new ArrayList<>();
    }

    @Nullable
    public Setting getSetting(String name, Module module){ // name of the setting and the parent module, returns null if the setting doesnt exist
        for(Setting s : settings){
            if(s.getName().equalsIgnoreCase(name) && s.getParent().equals(module))
                return s;
        }
        return null;
    }

	// get a list of all settings for a module
    public List<Setting> getSettingsForMod(Module module){
        List<Setting> list = new ArrayList<>();
        for(Setting s : settings){
            if(s.getParent().equals(module))
                list.add(s);
        }
        return list;
    }

    public Setting register(Setting setting){
        settings.add(setting);
        return setting;
    }
}
