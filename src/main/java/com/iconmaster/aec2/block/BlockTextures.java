package com.iconmaster.aec2.block;

import static com.iconmaster.aec2.util.SideUtils.BOTTOM;
import static com.iconmaster.aec2.util.SideUtils.TOP;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

/**
 *
 * @author iconmaster
 */
public class BlockTextures {
	public String[][] names;
	public IIcon[][] icons;
	public boolean allSides = false;

	public BlockTextures(String[][] names) {
		this.names = names;
	}

	public BlockTextures(String[] names) {
		this.names = new String[names.length][1];
		for (int i=0;i<names.length;i++) {
			this.names[i][0] = names[i];
		}
		this.allSides = true;
	}
	
	public BlockTextures(String names) {
		this.names = new String[1][1];
		this.names[0][0] = names;
		this.allSides = true;
	}
	
	public void register(IIconRegister iconRegister) {
		icons = new IIcon[names.length][names[0].length];
		for (int i=0;i<names.length;i++) {
			for (int j=0;j<names[i].length;j++) {
				icons[i][j] = iconRegister.registerIcon(names[i][j]);
			}
		}
	}
	
	public IIcon getTexture(int meta, int side) {
		if (meta>=icons.length) {
			return null;
		}
		
		if (allSides) {
			return icons[meta][0];
		} else {
			switch (side) {
				case (BOTTOM):
					return icons[meta][1];
				case (TOP):
					return icons[meta][0];
				default:
					return icons[meta][2];
			}
		}
	}
}
