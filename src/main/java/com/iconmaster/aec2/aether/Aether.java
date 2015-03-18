package com.iconmaster.aec2.aether;

/**
 *
 * @author iconmaster
 */
public enum Aether {
	MYARTIS(10, 3, 6, 1, 0x880000),
	TEGORIS(10, 2, 5, 3, 0x224400),
	SOLGEM(3, 10, 5, 2, 0x444400),
	TERREM(1, 10, 2, 7, 0x008800),
	HAETRONOUS(3, 4, 10, 3, 0x000088),
	GEOTOGOUS(1, 8, 10, 2, 0x004444),
	ALUOSA(1, 1, 8, 10, 0x222222),
	DRAYROSA(3, 6, 1, 10, 0x440044),
	
	PURIS(30, 1, 1, 1, 0xff0000), //of dust
	PUREM(1, 30, 1, 1, 0x00ff00), //of life
	PUROUS(1, 1, 30, 1, 0x0000ff), //of wood
	PUROSA(1, 1, 1, 30, 0xffff00), //of stone
	
	AETERNALIS(20, 20, 20, 20, 0xffffff); //ultimate power
	
	public String name;
	public int color;
	
	public int gleam;
	public int vigor;
	public int flair;
	public int mass;
	
	Aether(int g, int v, int f, int m, int color) {
		name = this.name().substring(0,1)+this.name().substring(1).toLowerCase();
		this.color = color;
		
		gleam = g;
		vigor = v;
		flair = f;
		mass = m;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
