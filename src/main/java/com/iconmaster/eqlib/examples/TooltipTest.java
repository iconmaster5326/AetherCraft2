package com.iconmaster.eqlib.examples;

import com.iconmaster.eqlib.EquivLib;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;

/**
 *
 * @author iconmaster
 */
public class TooltipTest {
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void eventHandler(ItemTooltipEvent event) {
		EntityPlayer player = event.entityPlayer;
		ItemStack stack = event.itemStack;
		List currenttip = event.toolTip;
		
		NumericSystem sys = EquivLib.sys;
		Double d = sys.getValue(stack);
		if (d!=null) {
			currenttip.add(d+" AV");
		}
	}
}
