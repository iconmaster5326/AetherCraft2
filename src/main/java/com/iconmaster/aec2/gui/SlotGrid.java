package com.iconmaster.aec2.gui;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

/**
 *
 * @author iconmaster
 */
public class SlotGrid {

	public int x;
	public int y;
	public int w;
	public int h;
	public Class<? extends Slot> clazz = Slot.class;

	public static final int SLOT_SIZE = 18;

	public SlotGrid(int x, int y, int w, int h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}
	
	public SlotGrid(int x, int y, int w, int h, Class<? extends Slot> clazz) {
		this(x, y, w, h);
		this.clazz = clazz;
	}

	public int addSlots(AetherCraftContainer inv, int i) {
		for (int xx = 0; xx < w; xx++) {
			for (int yy = 0; yy < h; yy++) {
				try {
					inv.addSlot(clazz.getConstructor(IInventory.class, int.class, int.class, int.class).newInstance(inv.te, i, x + xx * SLOT_SIZE, y + yy * SLOT_SIZE));
					i++;
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
		return i;
	}
}
