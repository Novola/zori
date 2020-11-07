package club.novola.zori.module.render;

import club.novola.zori.module.Module;
import club.novola.zori.setting.Setting;
import club.novola.zori.util.Wrapper;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class ShaderLoader extends Module {
    private Setting<ShaderLoader.Mode> shader = register("Shader", ShaderLoader.Mode.notch);

    public ShaderLoader() {
        super("ShaderLoader", Category.RENDER);
    }

    @Override
    public void onUpdate() {
        if (OpenGlHelper.shadersSupported && Wrapper.mc.getRenderViewEntity() instanceof EntityPlayer) {
            if (Wrapper.mc.entityRenderer.getShaderGroup() != null) {
                Wrapper.mc.entityRenderer.getShaderGroup().deleteShaderGroup();
            }
            try {
                Wrapper.mc.entityRenderer.loadShader(new ResourceLocation("shaders/post/" + this.shader.getValue() + ".json"));
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if (Wrapper.mc.entityRenderer.getShaderGroup() != null && Wrapper.mc.currentScreen == null) {
            Wrapper.mc.entityRenderer.getShaderGroup().deleteShaderGroup();
        }
    }

    @Override
    public void onDisable() {
        if (Wrapper.mc.entityRenderer.getShaderGroup() != null) {
            Wrapper.mc.entityRenderer.getShaderGroup().deleteShaderGroup();
        }
    }
    public enum Mode{
        notch, antialias, art, bits, blobs, blobs2, blur, bumpy, color_convolve, creeper, deconverge, desaturate, entity_outline, flip, fxaa, green, invert, ntsc, outline, pencil, phosphor, scan_pincusion, sobel, spider, wobble
    }
}
