package com.github.abigailfails.modicum.common.block.container;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

public class SizeLimitedSlot extends Slot {

    public SizeLimitedSlot(IInventory inventoryIn, int index, int xPosition, int yPosition) {
        super(inventoryIn, index, xPosition, yPosition);
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        return this.getStack().getCount() < this.inventory.getInventoryStackLimit() && stack.getCount() <= this.inventory.getInventoryStackLimit(); //just doesn't work
    }

    @Override
    public int getSlotStackLimit() {
        return super.getSlotStackLimit();
    }
}
