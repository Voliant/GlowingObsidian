package com.github.abigailfails.modicum.core.registry;

import com.github.abigailfails.modicum.common.block.AssemblerBlock;
import com.github.abigailfails.modicum.core.Modicum;
import com.minecraftabnormals.abnormals_core.core.util.registry.BlockSubRegistryHelper;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Modicum.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModicumBlocks {
    public static final BlockSubRegistryHelper HELPER = Modicum.REGISTRY_HELPER.getBlockSubHelper();

    public static final RegistryObject<Block> ASSEMBLER = HELPER.createBlock("assembler", () -> new AssemblerBlock(AbstractBlock.Properties.from(Blocks.DROPPER)), ItemGroup.REDSTONE);
}
