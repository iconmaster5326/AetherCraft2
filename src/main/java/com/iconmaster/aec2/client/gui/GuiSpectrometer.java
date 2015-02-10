package com.iconmaster.aec2.client.gui;

import com.iconmaster.aec2.aether.Aether;
import com.iconmaster.aec2.aether.Compound;
import com.iconmaster.aec2.aether.ItemConversionRegistry;
import com.iconmaster.aec2.gui.AetherCraftContainer;
import com.iconmaster.aec2.item.ItemCompound;
import com.iconmaster.aec2.te.AetherCraftTE;
import com.iconmaster.aec2.te.TESpectrometer;
import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

/**
 *
 * @author iconmaster
 */
public class GuiSpectrometer extends AetherCraftGui<TESpectrometer> {

	public GuiSpectrometer(InventoryPlayer player, AetherCraftContainer<TESpectrometer> container, AetherCraftTE te) {
		super(player, container, (TESpectrometer) te);
		
		gui_texture = new ResourceLocation("aec2", "textures/gui/spectrometerGui.png");
	}
	
	public static final int TEXT_X = 75;
	public static final int TEXT_Y = 16;
	public static final int TEXT_H = 8;
	public static final int TEXT_C = 0x404040;

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		
		ItemStack stack = te.inventory[0];
		
		if (stack!=null) {
			fontRendererObj.drawString(LanguageRegistry.instance().getStringLocalization("gui.aec2.spectrometer.name")+": "+stack.getDisplayName(), TEXT_X, TEXT_Y, TEXT_C);
			if (stack.getItem() instanceof ItemCompound) {
				NBTTagCompound tag = stack.getTagCompound();
				if (tag!=null) {
					Compound cpd = Compound.readFromNBT(tag);
					if (cpd!=null) {
						fontRendererObj.drawString(LanguageRegistry.instance().getStringLocalization("gui.aec2.spectrometer.compo")+":", TEXT_X, TEXT_Y + TEXT_H*2, TEXT_C);
						int i=0;
						int row=3;
						for (int amt : cpd.aethers) {
							if (amt>0) {
								Aether aether = Aether.values()[i];
								fontRendererObj.drawString(amt+" parts "+aether, TEXT_X + 5, TEXT_Y + TEXT_H*(row), TEXT_C);
								row++;
							}
							i++;
						}
					}
				}
			} else {
				Compound[] cpds = ItemConversionRegistry.getComposition(stack);
				if (cpds==null) {
					fontRendererObj.drawString(LanguageRegistry.instance().getStringLocalization("gui.aec2.spectrometer.compo.unknown"), TEXT_X, TEXT_Y + TEXT_H*2, TEXT_C);
				} else {
					fontRendererObj.drawString(LanguageRegistry.instance().getStringLocalization("gui.aec2.spectrometer.compo")+":", TEXT_X, TEXT_Y + TEXT_H*2, TEXT_C);
					int row=3;
					for (Compound cpd : cpds) {
						fontRendererObj.drawString(cpd.name, TEXT_X + 5, TEXT_Y + TEXT_H*(row), TEXT_C);
						row++;
					}
				}
			}
		}
	}
}
