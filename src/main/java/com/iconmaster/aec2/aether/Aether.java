package com.iconmaster.aec2.aether;

import com.iconmaster.aec2.AetherCraft;

/**
 *
 * @author iconmaster
 */
public enum Aether {
	MYARTIS(0x880000),
	SOLGEM(0x444400),
	HAETRONOUS(0x000088),
	ALUOSA(0x222222),
	TEGORIS(0x224400),
	GEOTOGOUS(0x004444),
	DRAYROSA(0x440044),
	TERREM(0x008800),
	
	PURIS(0xff0000),
	PUREM(0x00ff00),
	PUROUS(0x0000ff),
	PUROSA(0xffff00),
	
	AETERNALIS(0xffffff);
	
	public String name;
	public int color;
	
	public int gleam;
	public int vigor;
	public int flair;
	public int mass;
	
	Aether(int color) {
		name = this.name().substring(0,1)+this.name().substring(1).toLowerCase();
		this.color = color;
		
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
