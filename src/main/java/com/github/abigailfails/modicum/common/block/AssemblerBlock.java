package com.github.abigailfails.modicum.common.block;

import com.github.abigailfails.modicum.common.tileentity.AssemblerTileEntity;
import com.minecraftabnormals.abnormals_core.core.util.item.filling.TargetedItemGroupFiller;
import javafx.util.Pair;
import net.minecraft.block.BlockState;
import net.minecraft.block.DropperBlock;
import net.minecraft.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IDispenseItemBehavior;
import net.minecraft.dispenser.ProxyBlockSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.ICraftingRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.tileentity.DispenserTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.List;

public class AssemblerBlock extends DropperBlock {
    private static final TargetedItemGroupFiller FILLER = new TargetedItemGroupFiller(() -> Items.DISPENSER);
    private static final IDispenseItemBehavior DISPENSE_BEHAVIOR = new DefaultDispenseItemBehavior() {
        @Override
        protected void playDispenseSound(IBlockSource source) {
            World world = source.getWorld();
            world.playSound(null, source.getBlockPos(), SoundEvents.ENTITY_VILLAGER_WORK_MASON, SoundCategory.NEUTRAL, 0.7f+(world.rand.nextFloat()*0.6f), 0.7f+(world.rand.nextFloat()*0.6f));
        }
    };
    private Pair<List<ItemStack>, ICraftingRecipe> lastRecipe;

    public AssemblerBlock(Properties properties) {
        super(properties);
    }

    @Override
    public TileEntity createNewTileEntity(IBlockReader worldIn) {
        return new AssemblerTileEntity();
    }

    @Override
    protected void dispense(ServerWorld world, BlockPos pos) {
        AssemblerTileEntity assembler = (AssemblerTileEntity) world.getTileEntity(pos);
        if (assembler == null) return;
        CraftingInventory inventory = new CraftingInventory(new Container(null, -1) {
            @Override
            public boolean canInteractWith(PlayerEntity playerIn) {
                return false;
            }
        }, 3, 3);
        List<ItemStack> stacks = assembler.getStacks();
        for (int i = 0; i < 9; i++) inventory.setInventorySlotContents(i, stacks.get(i));
        ICraftingRecipe recipe = world.getRecipeManager().getRecipe(IRecipeType.CRAFTING, inventory, world).orElse(null);
        if (recipe == null) {
            world.playEvent(1001, pos, 0);
            //TODO failure particles if not empty
        } else {
            DISPENSE_BEHAVIOR.dispense(new ProxyBlockSource(world, pos), recipe.getCraftingResult(inventory));
            int i=0;
            for (ItemStack stack : recipe.getRemainingItems(inventory)) {
                assembler.setInventorySlotContents(i, stack);
                i++;
            }
        }
    }

    @SuppressWarnings("deprecation")
    public int getComparatorInputOverride(BlockState blockState, World world, BlockPos pos) {
        int filled = 0;
        DispenserTileEntity assembler = ((DispenserTileEntity)world.getTileEntity(pos));
        for (int i=0; i<9; i++) {
            if (assembler != null && !assembler.getStackInSlot(i).isEmpty()) filled++;
        }
        return (filled * 15) / 9;
    }

    @Override
    public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> stacks) {
        FILLER.fillItem(this.asItem(), group, stacks);
    }
}
