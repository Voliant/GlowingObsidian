package com.github.abigailfails.modicum.common.tileentity;

import com.github.abigailfails.modicum.common.block.container.AssemblerContainer;
import com.github.abigailfails.modicum.core.registry.ModicumTileEntities;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.DispenserTileEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.List;

public class AssemblerTileEntity extends DispenserTileEntity {

    public AssemblerTileEntity() {
        super(ModicumTileEntities.ASSEMBLER.get());
    }

    @Override
    public int getInventoryStackLimit() {
        return 1;
    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        return this.getStackInSlot(index).getCount() < this.getInventoryStackLimit() && stack.getCount() <= this.getInventoryStackLimit();
    }



    @Override
    public int getDispenseSlot() {
        return -1;
    }

    //Work out how to insert

    @Override
    protected ITextComponent getDefaultName() {
        return new TranslationTextComponent("container.modicum.assembler");
    }

    @Override
    protected Container createMenu(int id, PlayerInventory player) {
        return new AssemblerContainer(id, player, this);
    }

    public List<ItemStack> getStacks() {
        return this.getItems();
    }
}
