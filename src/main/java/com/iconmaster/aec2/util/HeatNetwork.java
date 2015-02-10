package com.iconmaster.aec2.util;

import java.util.ArrayList;
import java.util.HashMap;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 *
 * @author iconmaster
 */
public class HeatNetwork {
	public static class Vector3 {
		public int x;
		public int y;
		public int z;

		public Vector3(int x, int y, int z) {
			this.x = x;
			this.y = y;
			this.z = z;
		}

		@Override
		public int hashCode() {
			int hash = 5;
			hash = 29 * hash + this.x;
			hash = 29 * hash + this.y;
			hash = 29 * hash + this.z;
			return hash;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			final Vector3 other = (Vector3) obj;
			if (this.x != other.x) {
				return false;
			}
			if (this.y != other.y) {
				return false;
			}
			return this.z == other.z;
		}
	}
	
	public static ArrayList<Vector3> getItemsInNetwork(World w, int x, int y, int z) {
		ArrayList<Vector3> vecs = new ArrayList<HeatNetwork.Vector3>();
		HashMap<Vector3,Boolean> seen = new HashMap<Vector3, Boolean>();
		getItemsInNetwork(w, new Vector3(x, y, z), vecs, seen);
		return vecs;
	}
	
	public static void getItemsInNetwork(World w, Vector3 pos, ArrayList<Vector3> vecs, HashMap<Vector3,Boolean> seen) {
		seen.put(pos, true);
		for (int i : SideUtils.allSides) {
			SideUtils.Offset off = new SideUtils.Offset(i);
			Vector3 newVec = new Vector3(off.getOffsetX(pos.x), off.getOffsetY(pos.y), off.getOffsetZ(pos.z));
			if (!seen.containsKey(newVec)) {
				TileEntity b = w.getTileEntity(newVec.x, newVec.y, newVec.z);
				if (b!=null && b instanceof IHeatAcceptor) {
					vecs.add(newVec);
					getItemsInNetwork(w, newVec, vecs, seen);
				}
			}
		}
	}
	
	public static void addHeatToNetwork(World w, int x, int y, int z, int amt) {
		ArrayList<Vector3> bs = getItemsInNetwork(w, x, y, z);
		for (Vector3 vec : bs) {
			IHeatAcceptor b = (IHeatAcceptor) w.getTileEntity(vec.x, vec.y, vec.z);
			if (b.acceptsHeat()) {
				b.acceptHeat(amt);
				break;
			}
		}
	}
}
