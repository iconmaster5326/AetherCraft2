package com.iconmaster.aec2.aether;

import com.iconmaster.aec2.AetherCraft;

/**
 *
 * @author iconmaster
 */
public enum Aether {
	MYARTIS,SOLGEM,HAETRONOUS,ALUOSA,TEGORIS,GEOTOGOUS,DRAYROSA,TERREM;
	
	public String name;
	
	public int gleam;
	public int vigor;
	public int flair;
	public int mass;
	
	Aether() {
		name = this.name().substring(0,1)+this.name().substring(1).toLowerCase();
		
		gleam = 1+AetherCraft.aetherRandom.nextInt(10);
		vigor = 1+AetherCraft.aetherRandom.nextInt(10);
		flair = 1+AetherCraft.aetherRandom.nextInt(10);
		mass = 1+AetherCraft.aetherRandom.nextInt(10);
	}
	
	@Override
	public String toString() {
		return name;
	}
}
