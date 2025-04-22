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
import net.minecraft.util.math.MathHelper;
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
            updateCompassBehavior(stack, world, player);
        }

        return new TypedActionResult<>(ActionResult.SUCCESS, player.getStackInHand(hand));
    }

    public void updateCompassBehavior(ItemStack stack, World world, PlayerEntity player) {
        if (!world.isClient && player != null) {
            // Calculate the angle between the player and the target position
            double dx = targetPos.getX() + 0.5 - player.getX();
            double dz = targetPos.getZ() + 0.5 - player.getZ();
            double angle = Math.atan2(dz, dx) * (180 / Math.PI) - player.getYaw(1);

            // Normalize the angle to 0-360 degrees
            angle = MathHelper.floorMod((int) angle, 360) + 90;

            // Map the angle to a texture index (0-31)
            int textureIndex = (int) Math.round(angle / 11.25) % 32;

            // Store the texture index in the item's NBT data
            CompoundTag tag = stack.getOrCreateTag();
            tag.putInt("CustomModelData", textureIndex);
            stack.setTag(tag);
        }
    }

    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(new TranslatableText("item.manhunt-compass.manhunt_compass.tooltip", targetPos.getX(), targetPos.getY(), targetPos.getZ()));
        super.appendTooltip(stack, world, tooltip, context);
    }
}