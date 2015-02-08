package com.iconmaster.aec2.gui;

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

	public static final int SLOT_SIZE = 18;

	public SlotGrid(int x, int y, int w, int h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}

	public int addSlots(AetherCraftContainer inv, int i) {
		for (int xx = 0; xx < w; xx++) {
			for (int yy = 0; yy < h; yy++) {
				inv.addSlot(new Slot(inv.te, i, x + xx * SLOT_SIZE, y + yy * SLOT_SIZE));
				i++;
			}
		}
		return i;
	}
}
