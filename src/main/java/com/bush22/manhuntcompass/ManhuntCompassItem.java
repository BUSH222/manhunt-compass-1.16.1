package com.bush22.manhuntcompass;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class ManhuntCompassItem extends Item {
    private final BlockPos targetPos; // The coordinate the compass will point to

    public ManhuntCompassItem(Settings settings, BlockPos targetPos) {
        super(settings);
        this.targetPos = targetPos;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        if (!world.isClient) {
            ItemStack stack = player.getStackInHand(hand);
            CompoundTag tag = stack.getOrCreateTag();

            // Add a custom tooltip entry when the item is right-clicked
            tag.putString("item.manhunt-compass.manhunt_compass.tooltip", "Right-clicked at X: " + targetPos.getX() + ", Y: " + targetPos.getY() + ", Z: " + targetPos.getZ());
            stack.setTag(tag);

            // Optionally send a message to the player
            player.sendMessage(new TranslatableText("item.manhunt-compass.manhunt_compass.right_click", targetPos.getX(), targetPos.getY(), targetPos.getZ()), true);
        }

        return new TypedActionResult<>(ActionResult.SUCCESS, player.getStackInHand(hand));
    }

    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(new TranslatableText("item.manhunt-compass.manhunt_compass.tooltip", targetPos.getX(), targetPos.getY(), targetPos.getZ()));
        super.appendTooltip(stack, world, tooltip, context);
    }
}