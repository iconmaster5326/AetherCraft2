package com.iconmaster.aec2.client.gui;

import com.iconmaster.aec2.aether.Aether;
import com.iconmaster.aec2.aether.Compound;
import com.iconmaster.aec2.aether.ItemConversionRegistry;
import com.iconmaster.aec2.aether.ItemConversionRegistry.CRatio;
import com.iconmaster.aec2.gui.AetherCraftContainer;
import com.iconmaster.aec2.item.ItemCompound;
import com.iconmaster.aec2.te.AetherCraftTE;
import com.iconmaster.aec2.te.TESpectrometer;
import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.client.gui.GuiButton;
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
	
	@Override
	public void initGui() {
		super.initGui();
		
		button = new GuiButton(0, guiLeft+7, guiTop+50, 60, 20, LanguageRegistry.instance().getStringLocalization("gui.aec2.spectrometer.compo"));
		buttonList.add(button);
	}
	
	public static final int TEXT_X = 65;
	public static final int TEXT_Y = 16;
	public static final int TEXT_H = 8;
	public static final int TEXT_C = 0x404040;
	
	public GuiButton button;
	public boolean compoMode = true;

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		
		ItemStack stack = te.inventory[0];
		
		if (stack!=null) {
			if (stack.getItem() instanceof ItemCompound) {
				if (compoMode) {
					NBTTagCompound tag = stack.getTagCompound();
					if (tag!=null) {
						Compound cpd = Compound.readFromNBT(tag);
						if (cpd!=null) {
							fontRendererObj.drawString(LanguageRegistry.instance().getStringLocalization("gui.aec2.spectrometer.compo")+":", TEXT_X, TEXT_Y, TEXT_C);
							int i=0;
							int row=1;
							for (int amt : cpd.aethers) {
								if (amt>0) {
									Aether aether = Aether.values()[i];
									fontRendererObj.drawString(amt+" "+aether, TEXT_X + 5, TEXT_Y + TEXT_H*(row), TEXT_C);
									row++;
								}
								i++;
							}
						}
					}
				} else {
					NBTTagCompound tag = stack.getTagCompound();
					if (tag!=null) {
						Compound cpd = Compound.readFromNBT(tag);
						if (cpd!=null) {
							fontRendererObj.drawString(LanguageRegistry.instance().getStringLocalization("gui.aec2.spectrometer.props")+":", TEXT_X, TEXT_Y, TEXT_C);
							
							fontRendererObj.drawString(LanguageRegistry.instance().getStringLocalization("strings.aec2.hardness")+": "+cpd.hardness, TEXT_X + 5, TEXT_Y + TEXT_H*1, TEXT_C);
							fontRendererObj.drawString(LanguageRegistry.instance().getStringLocalization("strings.aec2.density")+": "+cpd.density, TEXT_X + 5, TEXT_Y + TEXT_H*2, TEXT_C);
							fontRendererObj.drawString(LanguageRegistry.instance().getStringLocalization("strings.aec2.reactivity")+": "+cpd.reactivity, TEXT_X + 5, TEXT_Y + TEXT_H*3, TEXT_C);
							fontRendererObj.drawString(LanguageRegistry.instance().getStringLocalization("strings.aec2.energy")+": "+cpd.energy, TEXT_X + 5, TEXT_Y + TEXT_H*4, TEXT_C);
							fontRendererObj.drawString(LanguageRegistry.instance().getStringLocalization("strings.aec2.stability")+": "+cpd.stability, TEXT_X + 5, TEXT_Y + TEXT_H*5, TEXT_C);
						}
					}
				}
			} else {
				if (compoMode) {
					CRatio[] cpds = ItemConversionRegistry.getComposition(stack).ratios;
					if (cpds==null) {
						fontRendererObj.drawString(LanguageRegistry.instance().getStringLocalization("gui.aec2.spectrometer.compo.unknown"), TEXT_X, TEXT_Y, TEXT_C);
					} else {
						fontRendererObj.drawString(LanguageRegistry.instance().getStringLocalization("gui.aec2.spectrometer.compo")+":", TEXT_X, TEXT_Y, TEXT_C);
						int row=1;
						for (CRatio cpd : cpds) {
							fontRendererObj.drawString(cpd.amt + " " + cpd.c.name, TEXT_X + 5, TEXT_Y + TEXT_H*(row), TEXT_C);
							row++;
						}
					}
				}
			}
		}
	}

	@Override
	protected void actionPerformed(GuiButton b) {
		if (compoMode) {
			compoMode = false;
			button.displayString = LanguageRegistry.instance().getStringLocalization("gui.aec2.spectrometer.props");
		} else {
			compoMode = true;
			button.displayString = LanguageRegistry.instance().getStringLocalization("gui.aec2.spectrometer.compo");
		}
	}
	
	
}
