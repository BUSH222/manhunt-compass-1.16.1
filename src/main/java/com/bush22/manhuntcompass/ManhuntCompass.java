package com.bush22.manhuntcompass;

import net.fabricmc.api.ModInitializer;
import net.minecraft.item.Item; // Add this import
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier; // Ensure this is imported
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry; // Ensure this is imported

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ManhuntCompass implements ModInitializer {
	public static final String MOD_ID = "manhunt-compass";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LogManager.getLogger(MOD_ID);
	    
	

	// things start here

	private static final BlockPos TARGET_COORDINATE = new BlockPos(100, 64, 200);

	public static final ManhuntCompassItem MANHUNT_COMPASS = new ManhuntCompassItem(
        new Item.Settings().group(ItemGroup.TOOLS), TARGET_COORDINATE
    );
	

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		LOGGER.info("Hello Fabric world!");

		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "manhunt_compass"), MANHUNT_COMPASS);
	}

	

	

}