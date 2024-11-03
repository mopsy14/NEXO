package mopsy.productions.nexo.screen.battery;

import mopsy.productions.nexo.ModBlocks.entities.energyStorage.BatteryMK1Entity;
import mopsy.productions.nexo.interfaces.IEnergyStorage;
import mopsy.productions.nexo.util.DisplayUtils;
import mopsy.productions.nexo.util.IntCords2D;
import mopsy.productions.nexo.util.ScreenUtils;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import static mopsy.productions.nexo.Main.modid;

public class BatteryMK1Screen extends HandledScreen<BatteryMK1ScreenHandler> {
    private static final Identifier TEXTURE = Identifier.of(modid, "textures/gui/battery.png");
    public Predicate<IntCords2D> renderEnergyTooltipPredicate;

    public BatteryMK1Screen(BatteryMK1ScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void init() {
        super.init();
        titleX = (backgroundWidth-textRenderer.getWidth(title))/2;
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        int x = (width - backgroundWidth)/2;
        int y = (height - backgroundHeight)/2;
        context.drawTexture(RenderLayer::getGuiTextured,TEXTURE, x, y, 0, 0, backgroundWidth, backgroundHeight,256,256);

        renderEnergyTooltipPredicate = ScreenUtils.renderEnergyStorage(this, context, x+80, y+17, getPower(), BatteryMK1Entity.POWER_CAPACITY);
    }
    

    @Override
    protected void drawMouseoverTooltip(DrawContext context, int x, int y) {
        super.drawMouseoverTooltip(context, x, y);
        IntCords2D mouse = new IntCords2D(x,y);
        if (renderEnergyTooltipPredicate.test(mouse))
            renderEnergyTooltip(hasShiftDown(), context, mouse);
    }

    @Override
    protected void drawForeground(DrawContext context, int mouseX, int mouseY) {
        context.drawText(this.textRenderer, this.title, this.titleX, this.titleY, 0x404040,false);
    }

    private void renderEnergyTooltip(boolean exact, DrawContext context, IntCords2D mouseCords){
        List<Text> text = new ArrayList<>();
        text.add(Text.of(DisplayUtils.getEnergyBarText(getPower(), BatteryMK1Entity.POWER_CAPACITY, hasShiftDown())));
        if(!exact)
            text.add(Text.of("Hold shift for advanced view"));
        context.drawTooltip(textRenderer, text, mouseCords.x, mouseCords.y);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context,mouseX,mouseY,delta);
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context, mouseX, mouseY);
    }

    private long getPower(){
        BlockEntity blockEntity = MinecraftClient.getInstance().world.getBlockEntity(handler.getBlockPos());
        if(blockEntity instanceof IEnergyStorage) {
            return ((IEnergyStorage) blockEntity).getPower();
        }
        return 0;
    }
}
