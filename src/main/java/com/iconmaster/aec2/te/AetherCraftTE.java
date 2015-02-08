package com.iconmaster.aec2.te;

import com.iconmaster.aec2.item.ItemCompound;
import com.iconmaster.aec2.util.SideUtils;
import java.util.Arrays;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

/**
 *
 * @author iconmaster
 */
public abstract class AetherCraftTE extends TileEntity implements ISidedInventory {
	public ItemStack[] inventory;
	public int invSize = 0;
	public String name = "aec.te";
	public int tick = 0;

	public AetherCraftTE() {
		setup();
		inventory = new ItemStack[invSize];
	}
	
	public abstract void setup();
	
	@Override
	public int getSizeInventory() {
		return invSize;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		return inventory[slot];
	}
	
	@Override
	public ItemStack decrStackSize(int slot, int amount) {
		ItemStack stack = this.getStackInSlot(slot);

		if (stack != null) {
			if (stack.stackSize <= amount) {
				this.setInventorySlotContents(slot, null);
			} else {
				stack = stack.splitStack(amount);

				if (stack.stackSize == 0) {
					this.setInventorySlotContents(slot, null);
				}
			}
		}

		return stack;
	}
	
	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		ItemStack stack = this.getStackInSlot(slot);

		if (stack != null) {
			setInventorySlotContents(slot, null);
		}

		return stack;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		this.inventory[slot] = stack;

		if (stack != null && stack.stackSize > this.getInventoryStackLimit()) {
			stack.stackSize = this.getInventoryStackLimit();
		}
	}
	
	@Override
	public String getInventoryName() {
		return name;
	}

	@Override
	public boolean hasCustomInventoryName() {
		return true;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return this.worldObj.getTileEntity(this.xCoord, this.yCoord,this.zCoord) == this && player.getDistanceSq(this.xCoord + 0.5, this.yCoord + 0.5, this.zCoord + 0.5) < 64;
	}

	@Override
	public void openInventory() {
	}

	@Override
	public void closeInventory() {
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		if (slot > 0) {
			return true;
		}

		return false;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tagCompound) {
		super.readFromNBT(tagCompound);

		NBTTagList tagList = tagCompound.getTagList("Inventory", 10);

		for (int i = 0; i < tagList.tagCount(); i++) {
			NBTTagCompound tag = (NBTTagCompound) tagList.getCompoundTagAt(i);
			byte slot = tag.getByte("Slot");

			if (slot >= 0 && slot < inventory.length) {
				inventory[slot] = ItemStack.loadItemStackFromNBT(tag);
			}
		}
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);
		NBTTagList itemList = new NBTTagList();

		for (int i = 0; i < inventory.length; i++) {
			ItemStack stack = inventory[i];

			if (stack != null) {
				NBTTagCompound tag = new NBTTagCompound();
				tag.setByte("Slot", (byte) i);
				stack.writeToNBT(tag);
				itemList.appendTag(tag);
			}
		}

		tagCompound.setTag("Inventory", itemList);
	}
	
	@Override
	public void updateEntity() {
		tick++;
		if (tick%4==0) {
			update();
		}
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return SideUtils.allSides;
	}

	@Override
	public boolean canInsertItem(int p_102007_1_, ItemStack p_102007_2_, int p_102007_3_) {
		return true;
	}

	@Override
	public boolean canExtractItem(int p_102008_1_, ItemStack p_102008_2_, int p_102008_3_) {
		return true;
	}
	
	public int getMetadata() {
		return this.worldObj.getBlockMetadata(this.xCoord,this.yCoord,this.zCoord);
	}
	
	public int getFilledSlot(int begin, int end) {
		for (int i=begin;i<end;i++) {
			if (inventory[i]!=null) {
				return i;
			}
		}
		return -1;
	}
	
	public int getFilledSlot() {
		return getFilledSlot(0,invSize);
	}
	
	public int getEmptySlot(int begin, int end) {
		for (int i=begin;i<end;i++) {
			if (inventory[i]==null) {
				return i;
			}
		}
		return -1;
	}
	
	public int getEmptySlot() {
		return getFilledSlot(0,invSize);
	}
	
	public int getStackableSlot(ItemStack item, int begin, int end) {
		for (int i=begin;i<end;i++) {
			if (inventory[i]==null || areStackable(item, inventory[i])) {
				return i;
			}
		}
		return getEmptySlot(begin, end);
	}
	
	public int getStackableSlot(ItemStack item) {
		return getStackableSlot(item, 0, invSize);
	}
	
	public static boolean areStackable(ItemStack stack1, ItemStack stack2) {
		if (!stack1.isItemEqual(stack2)) {
			return false;
		}
		
		if (!stack1.isStackable()) {
			return false;
		}
		
		if ((stack1.stackSize+stack2.stackSize)>stack1.getMaxStackSize()) {
			return false;
		}
		
		if (stack1.getItem() instanceof ItemCompound) {
			if (stack1.getTagCompound()==null || stack2.getTagCompound()==null) {
				return false;
			}
			byte[] b1 = stack1.getTagCompound().getByteArray("cpd");
			byte[] b2 = stack2.getTagCompound().getByteArray("cpd");
			
			if (!Arrays.equals(b1, b2)) {
				return false;
			}
		}
		
		return true;
	}

	public abstract void update();
}
