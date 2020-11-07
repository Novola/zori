package club.novola.zori.module;

import club.novola.zori.Zori;
import club.novola.zori.command.Command;
import club.novola.zori.managers.ModuleManager;
import club.novola.zori.module.client.Settings;
import club.novola.zori.setting.Setting;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraftforge.common.MinecraftForge;

public abstract class Module {
    private final String name;
    private final Category category;
    private int bind;
    private boolean enabled;
    private Setting<BindBehaviour> bindBehaviour;

    public Module(String name, Category category){
        this.name = name;
        this.category = category;
        this.bind = 0; // 0 = none
        this.enabled = false;
        bindBehaviour = register("BindMode", BindBehaviour.TOGGLE);
        ModuleManager.register(this, name);
    }

    public Module(String name, Category category, boolean enabled, BindBehaviour bindBehaviourIn){
        this.name = name;
        this.category = category;
        this.bind = 0;
        this.enabled = enabled;
        bindBehaviour = register("BindMode", bindBehaviourIn);
        ModuleManager.register(this, name);
    }

    public String getName() {
        return name;
    }

    public Category getCategory() {
        return category;
    }

    public int getBind() {
        return bind;
    }
	
	// used for array list which is not actually implemented
    public String getHudInfo(){
        return "";
    }

    /**
     * @return new bind
     */
    public int setBind(int newBind){
        bind = newBind;
        return bind;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void enable(){
        enabled = true;
        if(Settings.togglemsgs) {
            Command.sendClientMessage(getName() + ChatFormatting.GREEN + " ON", false);
        }
        MinecraftForge.EVENT_BUS.register(this);
        Zori.getInstance().moduleManager.getEnabledModules().remove(this);
        Zori.getInstance().moduleManager.getEnabledModules().add(this);
        onEnable();
    }

    public void disable(){
        enabled = false;
        if(Settings.togglemsgs) {
            Command.sendClientMessage(getName() + ChatFormatting.RED + " OFF", false);
        }
        MinecraftForge.EVENT_BUS.unregister(this);
        Zori.getInstance().moduleManager.getEnabledModules().remove(this);
        onDisable();
    }

    public boolean toggle(){
        if(isEnabled()) disable();
        else enable();
        return isEnabled();
    }

    public BindBehaviour getBindBehaviour(){
        return bindBehaviour.getValue();
    }

    protected void onEnable(){} // called when the module is enabled
    protected void onDisable(){} // called when the module is disabled
    public void onUpdate(){} // called every client tick
    public void preRender2D(){} // called before the game overlay is rendered
    public void postRender2D(){} // called after the hotbar is rendered
    public void onRender3D(){} // for 3D rendering

    public enum Category{
        COMBAT, RENDER, PLAYER, MOVEMENT, HUD, MISC, CLIENT
    }

    public enum BindBehaviour{
        TOGGLE, HOLD
    }

	// used to register a setting
    protected Setting register(String name, Object value){
        return Zori.getInstance().settingManager.register(new Setting<>(name, value, this));
    }

	// used to register number settings, min and max are used for sliders
	// make sure to format the numbers properly - if its a float setting use 69f, for double use 69d, otherwise its gonna assume its and integer and throw and exception
    protected Setting register(String name, Object value, Object min, Object max){
        return Zori.getInstance().settingManager.register(new Setting<>(name, value, min, max, this));
    }
}
