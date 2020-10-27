package club.novola.zori.module.misc;

import club.novola.zori.setting.Setting;
import club.novola.zori.module.Module;
import club.novola.zori.util.Wrapper;

public class Sprint extends Module {
    public Sprint() {
        super("Sprint", Category.MISC);
    }

    @SuppressWarnings("unchecked")
    private Setting<Boolean> onlyForward = register("OnlyForward", false);

    public void onUpdate(){
        if(Wrapper.getPlayer() == null) return;
        if(onlyForward.getValue()){
            if(Wrapper.getPlayer().moveForward > 0) Wrapper.getPlayer().setSprinting(true);
        } else {
            if(Wrapper.getPlayer().moveForward != 0) Wrapper.getPlayer().setSprinting(true);
        }
    }
}
