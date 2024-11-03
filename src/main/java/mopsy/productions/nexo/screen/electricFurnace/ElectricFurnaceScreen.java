package mopsy.productions.nexo.screen.electricFurnace;

import com.mojang.blaze3d.systems.RenderSystem;
import mopsy.productions.nexo.ModBlocks.entities.machines.ElectricFurnaceEntity;
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

public class ElectricFurnaceScreen extends HandledScreen<ElectricFurnaceScreenHandler> {
    private static final Identifier TEXTURE = Identifier.of(modid, "textures/gui/electric_furnace.png");
    public Predicate<IntCords2D> renderEnergyTooltipPredicate;

    public ElectricFurnaceScreen(ElectricFurnaceScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }
    @Override
    protected void drawForeground(DrawContext context, int mouseX, int mouseY) {
        context.drawText(textRenderer, this.title, this.titleX, this.titleY, 0x404040,false);
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

        renderEnergyTooltipPredicate = ScreenUtils.renderEnergyStorage(this, context, x+147, y+11, getPower(), ElectricFurnaceEntity.POWER_CAPACITY);
        renderProgress(context, x, y);
    }

    @Override
    protected void drawMouseoverTooltip(DrawContext context, int x, int y) {
        super.drawMouseoverTooltip(context, x, y);
        IntCords2D mouse = new IntCords2D(x,y);
        if (renderEnergyTooltipPredicate.test(mouse))
            renderEnergyTooltip(hasShiftDown(), context, mouse);
    }
    private void renderEnergyTooltip(boolean exact, DrawContext context, IntCords2D mouseCords){
        List<Text> text = new ArrayList<>();
        text.add(Text.of(DisplayUtils.getEnergyBarText(getPower(), ElectricFurnaceEntity.POWER_CAPACITY, hasShiftDown())));
        if(!exact)
            text.add(Text.of("Hold shift for advanced view"));
        context.drawTooltip(textRenderer, text, mouseCords.x, mouseCords.y);
    }

    private void renderProgress(DrawContext context, int x, int y){
        if(handler.isCrafting()){
            RenderSystem.setShaderTexture(0, TEXTURE);
            context.drawTexture(RenderLayer::getGuiTextured,TEXTURE, x+77, y+34, 192, 14, handler.getScaledProgress(), 16,256,256);
        }
        if(handler.isHeating()) {
            RenderSystem.setShaderTexture(0, TEXTURE);
            context.drawTexture(RenderLayer::getGuiTextured,TEXTURE, x + 48, y + 54, 192, 0, 13, 13,256,256);
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context, mouseX, mouseY, delta);
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
