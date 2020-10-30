package club.novola.zori.mixin.mixins;

import club.novola.zori.Zori;
import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ChatLine;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Mixin(GuiNewChat.class)
@SideOnly(Side.CLIENT)
public class MixinGuiNewChat extends Gui {
    @Shadow
    @Final
    private static Logger LOGGER = LogManager.getLogger();

    @Shadow
    @Final
    private Minecraft mc;

    @Shadow
    @Final
    private List<ChatLine> drawnChatLines = Lists.<ChatLine>newArrayList();

    @Shadow
    private int scrollPos;

    @Shadow
    private boolean isScrolled;

    @Shadow
    private void setChatLine(ITextComponent chatComponent, int chatLineId, int updateCounter, boolean displayOnly){}

	// this is a horrible way to do this and might break other mods so you probably wanna just remove it
    @Inject(method = "drawChat", at = @At("HEAD"), cancellable = true)
    private void drawChat(int updateCounter, CallbackInfo ci){
        if(mc.ingameGUI == null || !Zori.getInstance().moduleManager.getModuleByName("Chat").isEnabled()) return;
        GuiNewChat chat = mc.ingameGUI.getChatGUI();

        ci.cancel(); // my method now bitch

        if (this.mc.gameSettings.chatVisibility != EntityPlayer.EnumChatVisibility.HIDDEN)
        {
            int i = chat.getLineCount();
            int j = this.drawnChatLines.size();
            float f = this.mc.gameSettings.chatOpacity * 0.9F + 0.1F;

            if (j > 0)
            {
                boolean flag = false;

                if (chat.getChatOpen())
                {
                    flag = true;
                }

                float f1 = chat.getChatScale();
                //int k = MathHelper.ceil((float)chat.getChatWidth() / f1);
                GlStateManager.pushMatrix();
                GlStateManager.translate(2.0F, 8.0F, 0.0F);
                GlStateManager.scale(f1, f1, 1.0F);
                int l = 0;

                for (int i1 = 0; i1 + this.scrollPos < this.drawnChatLines.size() && i1 < i; ++i1)
                {
                    ChatLine chatline = this.drawnChatLines.get(i1 + this.scrollPos);

                    if (chatline != null)
                    {
                        int j1 = updateCounter - chatline.getUpdatedCounter();

                        if (j1 < 200 || flag)
                        {
                            double d0 = (double)j1 / 200.0D;
                            d0 = 1.0D - d0;
                            d0 = d0 * 10.0D;
                            d0 = MathHelper.clamp(d0, 0.0D, 1.0D);
                            d0 = d0 * d0;
                            int l1 = (int)(255.0D * d0);

                            if (flag)
                            {
                                l1 = 255;
                            }

                            l1 = (int)((float)l1 * f);
                            ++l;

                            if (l1 > 3)
                            {
                                //int i2 = 0;
                                int j2 = -i1 * 9;
                                String s = chatline.getChatComponent().getFormattedText();
                                boolean name = mc.player != null && s.toLowerCase().contains(mc.player.getName().toLowerCase());
                                //drawRect(-2, j2 - 9, 0 + k + 4, j2, l1 / 2 << 24);
                                if(name)
                                    drawRect(mc.fontRenderer.getStringWidth("<00:00> ") - 1, j2 - 9, mc.fontRenderer.getStringWidth(s) + 1, j2 + 1, Zori.getInstance().clientSettings.getColor(69));
                                GlStateManager.enableBlend();
                                int color = Zori.getInstance().clientSettings.getColor() + (l1 << 24);
                                if(color < Integer.MAX_VALUE)
                                    mc.fontRenderer.drawStringWithShadow(s.split(" ")[0], 0f, (float)(j2 - 8), color);
                                this.mc.fontRenderer.drawStringWithShadow(s.substring(6), mc.fontRenderer.getStringWidth("<00:00> "), (float)(j2 - 8), 16777215 + (l1 << 24));
                                GlStateManager.disableAlpha();
                                GlStateManager.disableBlend();
                            }
                        }
                    }
                }

                if (flag)
                {
                    int k2 = this.mc.fontRenderer.FONT_HEIGHT;
                    GlStateManager.translate(-3.0F, 0.0F, 0.0F);
                    int l2 = j * k2 + j;
                    int i3 = l * k2 + l;
                    int j3 = this.scrollPos * i3 / j;
                    int k1 = i3 * i3 / l2;

                    if (l2 != i3)
                    {
                        int k3 = j3 > 0 ? 170 : 96;
                        int l3 = this.isScrolled ? 13382451 : 3355562;
                        drawRect(0, -j3, 2, -j3 - k1, l3 + (k3 << 24));
                        drawRect(2, -j3, 1, -j3 - k1, 13421772 + (k3 << 24));
                    }
                }

                GlStateManager.popMatrix();
            }
        }
    }

	// chat timestamps, also not perfect
    @Inject(method = "printChatMessageWithOptionalDeletion", at = @At("HEAD"), cancellable = true)
    private void printChatMessageWithOptionalDeletion(ITextComponent chatComponent, int chatLineId, CallbackInfo ci){
        ci.cancel();

        String time = new SimpleDateFormat("HH:mm ").format(new Date());
        ITextComponent orig = chatComponent;
        chatComponent = new TextComponentString(time).appendSibling(orig);

        this.setChatLine(chatComponent, chatLineId, this.mc.ingameGUI.getUpdateCounter(), false);
        LOGGER.info("[CHAT] {}", (Object)chatComponent.getUnformattedText().replaceAll("\r", "\\\\r").replaceAll("\n", "\\\\n"));
    }
}
