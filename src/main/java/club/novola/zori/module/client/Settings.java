package club.novola.zori.module.client;

import club.novola.zori.Zori;
import club.novola.zori.setting.Setting;
import club.novola.zori.module.Module;
import club.novola.zori.util.Wrapper;

@SuppressWarnings("unchecked")
public class Settings extends Module {
    public Settings() {
        super("Settings", Category.CLIENT);
    }

    public Setting<String> commandPrefix = register("Prefix", ",");
    private Setting<Boolean> customFOV = register("CustomFOV", false);
    private Setting<Integer> fov = Zori.getInstance().settingManager.register(new Setting<Integer>("FOV", 130, 90, 180, this){
		// set mc's fov setting to the value whenever it changes
        @Override
        public Integer setValue(Integer value){
            if(customFOV.getValue() && Wrapper.mc.gameSettings.fovSetting != fov.getValue().floatValue())
                Wrapper.mc.gameSettings.fovSetting = value.floatValue();
            return super.setValue(value);
        }
    });

    @Override
    public void onEnable(){
        if(customFOV.getValue() && Wrapper.mc.gameSettings.fovSetting != fov.getValue().floatValue()){
            Wrapper.mc.gameSettings.fovSetting = fov.getValue().floatValue();
        }
    }
}
