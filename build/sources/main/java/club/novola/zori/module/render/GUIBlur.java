package club.novola.zori.module.render;

import club.novola.zori.module.Module;
import club.novola.zori.module.hud.ClickGuiModule;
import club.novola.zori.util.Wrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.advancements.GuiAdvancement;
import net.minecraft.client.gui.advancements.GuiAdvancementTab;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiEditSign;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.GuiModList;

public class GUIBlur extends Module {

    final Minecraft mc = Minecraft.getMinecraft();

    public GUIBlur() {
        super("GUIBlur", Category.RENDER);
    }

    public void onDisable() {
        if (mc.world != null) {
            Wrapper.mc.entityRenderer.getShaderGroup().deleteShaderGroup();
        }
    }

    public void onUpdate() {
        if (mc.world != null) {
            if (ClickGuiModule.enabledGui || mc.currentScreen instanceof GuiContainer || mc.currentScreen instanceof GuiChat
                    || mc.currentScreen instanceof GuiConfirmOpenLink || mc.currentScreen instanceof GuiEditSign
                    || mc.currentScreen instanceof GuiGameOver || mc.currentScreen instanceof GuiOptions
                    || mc.currentScreen instanceof GuiIngameMenu || mc.currentScreen instanceof GuiVideoSettings
                    || mc.currentScreen instanceof GuiScreenOptionsSounds || mc.currentScreen instanceof GuiControls
                    || mc.currentScreen instanceof GuiCustomizeSkin || mc.currentScreen instanceof GuiModList){

                if (OpenGlHelper.shadersSupported && Wrapper.mc.getRenderViewEntity() instanceof EntityPlayer) {
                    if (Wrapper.mc.entityRenderer.getShaderGroup() != null) {
                        Wrapper.mc.entityRenderer.getShaderGroup().deleteShaderGroup();
                    }
                    try {
                        Wrapper.mc.entityRenderer.loadShader(new ResourceLocation("shaders/post/blur.json"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (Wrapper.mc.entityRenderer.getShaderGroup() != null && Wrapper.mc.currentScreen == null) {
                    Wrapper.mc.entityRenderer.getShaderGroup().deleteShaderGroup();
                }
            }else{
                if (Wrapper.mc.entityRenderer.getShaderGroup() != null) {
                    Wrapper.mc.entityRenderer.getShaderGroup().deleteShaderGroup();
                }
            }
        }
    }
}
