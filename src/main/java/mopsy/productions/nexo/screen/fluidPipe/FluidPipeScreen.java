package mopsy.productions.nexo.screen.fluidPipe;

import mopsy.productions.nexo.ModBlocks.entities.transport.FluidPipe_MK1Entity;
import mopsy.productions.nexo.networking.payloads.FluidPipeStateCyclePayload;
import mopsy.productions.nexo.util.IntCords2D;
import mopsy.productions.nexo.util.NEXORotation;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import static mopsy.productions.nexo.Main.modid;
import static mopsy.productions.nexo.util.NEXORotation.*;

public class FluidPipeScreen extends HandledScreen<FluidPipeScreenHandler> {
    private static final Identifier TEXTURE = Identifier.of(modid, "textures/gui/fluid_pipe.png");
    public Predicate<IntCords2D> renderUpTooltipPredicate;
    public Predicate<IntCords2D> renderDownTooltipPredicate;
    public Predicate<IntCords2D> renderNorthTooltipPredicate;
    public Predicate<IntCords2D> renderEastTooltipPredicate;
    public Predicate<IntCords2D> renderSouthTooltipPredicate;
    public Predicate<IntCords2D> renderWestTooltipPredicate;
    public Predicate<IntCords2D> renderEnergyTooltipPredicate;

    public FluidPipeScreen(FluidPipeScreenHandler handler, PlayerInventory inventory, Text title) {
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


        if(MinecraftClient.getInstance().world.getBlockEntity(handler.getBlockPos()) instanceof FluidPipe_MK1Entity entity) {
            renderUpTooltipPredicate = renderSlot(context, x+19, y+29, TEXTURE, entity, UP);
            renderDownTooltipPredicate = renderSlot(context, x+19, y+69, TEXTURE, entity, DOWN);
            renderNorthTooltipPredicate = renderSlot(context, x+69, y+29, TEXTURE, entity, NORTH);
            renderEastTooltipPredicate = renderSlot(context, x+89, y+49, TEXTURE, entity, EAST);
            renderSouthTooltipPredicate = renderSlot(context, x+69, y+69, TEXTURE, entity, SOUTH);
            renderWestTooltipPredicate = renderSlot(context, x+49, y+49, TEXTURE, entity, WEST);
        }
    }

    public Predicate<IntCords2D> renderSlot(DrawContext context, int x, int y, Identifier texture, FluidPipe_MK1Entity entity, NEXORotation rotation){

        if(entity.endStates.get(rotation).isInput()) {
            context.drawTexture(RenderLayer::getGuiTextured,texture, x, y, 176, 0, 20, 20,256,256);
            context.drawItem(entity.getWorld().getBlockState(entity.getPos().add(rotation.transformToVec3i())).getBlock().asItem().getDefaultStack(),x+2,y+2);
        }else if(entity.endStates.get(rotation).isOutput()){
            context.drawTexture(RenderLayer::getGuiTextured,texture, x, y, 196, 0, 20, 20,256,256);
            context.drawItem(entity.getWorld().getBlockState(entity.getPos().add(rotation.transformToVec3i())).getBlock().asItem().getDefaultStack(),x+2,y+2);
        }else{
            context.drawTexture(RenderLayer::getGuiTextured,texture, x, y, 216, 0, 20, 20,256,256);
            context.drawItem(entity.getWorld().getBlockState(entity.getPos().add(rotation.transformToVec3i())).getBlock().asItem().getDefaultStack(),x+2,y+2);
        }

        return ic2d -> (
                ic2d.x>x+2 && ic2d.x<x+18 &&
                        ic2d.y>y+2 && ic2d.y<y+18
        );
    }

    @Override
    protected void drawMouseoverTooltip(DrawContext context, int x, int y) {
        super.drawMouseoverTooltip(context, x, y);
        if(MinecraftClient.getInstance().world.getBlockEntity(handler.getBlockPos()) instanceof FluidPipe_MK1Entity entity) {
            IntCords2D mouse = new IntCords2D(x, y);
            if (renderUpTooltipPredicate.test(mouse))
                renderSlotTooltip(context, mouse, entity, UP);
            if (renderDownTooltipPredicate.test(mouse))
                renderSlotTooltip(context, mouse, entity, DOWN);
            if (renderNorthTooltipPredicate.test(mouse))
                renderSlotTooltip(context, mouse, entity, NORTH);
            if (renderEastTooltipPredicate.test(mouse))
                renderSlotTooltip(context, mouse, entity, EAST);
            if (renderSouthTooltipPredicate.test(mouse))
                renderSlotTooltip(context, mouse, entity, SOUTH);
            if (renderWestTooltipPredicate.test(mouse))
                renderSlotTooltip(context, mouse, entity, WEST);
        }
    }

    @Override
    protected void drawForeground(DrawContext context, int mouseX, int mouseY) {
        context.drawText(textRenderer, this.title, this.titleX, this.titleY, 0x404040,false);
    }

    private void renderSlotTooltip(DrawContext context, IntCords2D mouseCords, FluidPipe_MK1Entity entity, NEXORotation rotation){
        List<Text> text = new ArrayList<>();
        text.add(Text.of(Formatting.GOLD+"Side: "));
        text.add(Text.of(rotation.id.toUpperCase()));
        text.add(Text.of(Formatting.GOLD+"Block name:"));
        text.add(Text.translatable(entity.getWorld().getBlockState(entity.getPos().add(rotation.transformToVec3i())).getBlock().getTranslationKey()));
        text.add(Text.of(Formatting.GOLD+"Current mode: "));
        text.add(Text.of(entity.endStates.get(rotation).toString()));
        if(entity.endStates.get(rotation).isEnd())
            text.add(Text.of(Formatting.ITALIC+"Click to cycle mode"));
        if(entity.endStates.get(rotation).isPipe())
            text.add(Text.of(Formatting.ITALIC+"Connected to other pipe"));
        if(entity.endStates.get(rotation).isNone())
            text.add(Text.of(Formatting.ITALIC+"No fluid storage found"));
        context.drawTooltip(textRenderer, text, mouseCords.x, mouseCords.y);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context, mouseX, mouseY, delta);
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context, mouseX, mouseY);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {

        IntCords2D ic2d = new IntCords2D(Math.toIntExact(Math.round(mouseX)),Math.toIntExact(Math.round(mouseY)));
        if(renderUpTooltipPredicate.test(ic2d)){
            if(MinecraftClient.getInstance().world.getBlockEntity(handler.getBlockPos()) instanceof FluidPipe_MK1Entity entity) {
                if (entity.endStates.get(UP).isEnd())
                    sendCycleStatePacket(UP);
            }
        }else if(renderDownTooltipPredicate.test(ic2d)) {
            if (MinecraftClient.getInstance().world.getBlockEntity(handler.getBlockPos()) instanceof FluidPipe_MK1Entity entity) {
                if (entity.endStates.get(DOWN).isEnd())
                    sendCycleStatePacket(DOWN);
            }
        }else if(renderNorthTooltipPredicate.test(ic2d)) {
            if (MinecraftClient.getInstance().world.getBlockEntity(handler.getBlockPos()) instanceof FluidPipe_MK1Entity entity) {
                if (entity.endStates.get(NORTH).isEnd())
                    sendCycleStatePacket(NORTH);
            }
        }else if(renderEastTooltipPredicate.test(ic2d)) {
            if (MinecraftClient.getInstance().world.getBlockEntity(handler.getBlockPos()) instanceof FluidPipe_MK1Entity entity) {
                if (entity.endStates.get(EAST).isEnd())
                    sendCycleStatePacket(EAST);
            }
        }else if(renderSouthTooltipPredicate.test(ic2d)) {
            if (MinecraftClient.getInstance().world.getBlockEntity(handler.getBlockPos()) instanceof FluidPipe_MK1Entity entity) {
                if (entity.endStates.get(SOUTH).isEnd())
                    sendCycleStatePacket(SOUTH);
            }
        }else if(renderWestTooltipPredicate.test(ic2d)) {
            if (MinecraftClient.getInstance().world.getBlockEntity(handler.getBlockPos()) instanceof FluidPipe_MK1Entity entity) {
                if (entity.endStates.get(WEST).isEnd())
                    sendCycleStatePacket(WEST);
            }
        }


        return super.mouseClicked(mouseX, mouseY, button);
    }
    private void sendCycleStatePacket(NEXORotation rotation){
        ClientPlayNetworking.send(new FluidPipeStateCyclePayload(handler.getBlockPos(),rotation));
    }
}
