package club.novola.zori.hud.components;

import club.novola.zori.hud.HudComponent;
import club.novola.zori.module.hud.Logo;
import club.novola.zori.util.Wrapper;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class LogoComponent extends HudComponent<Logo> {

    private ResourceLocation logo;

    public LogoComponent() {
        super("Logo", 100, 100, Logo.INSTANCE);
        this.onLoad();
    }

    public void render() {
        Wrapper.mc.renderEngine.bindTexture(this.logo);
        this.width = module.imageWidth.getValue();
        this.height = module.imageHeight.getValue();
        GlStateManager.color(255.0f, 255.0f, 255.0f);
        Gui.drawScaledCustomSizeModalRect((int)this.x + 4, (int)this.y + 4, 7.0f, 7.0f, (int)this.width - 7, (int)this.height - 7, (int)this.width, (int)this.height, (float)this.width, (float)this.height);
    }

    private void onLoad() {
        try {
            if (module.waoLogo.getValue()) {
                logo = new ResourceLocation("logos/wao.png");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
