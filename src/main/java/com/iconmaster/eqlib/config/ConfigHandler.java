package com.iconmaster.eqlib.config;

import com.iconmaster.eqlib.EquivLib;
import com.iconmaster.eqlib.recipe.EquivHandler;
import com.iconmaster.eqlib.recipe.ItemData;
import com.iconmaster.eqlib.recipe.RecipeMap;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import net.minecraftforge.common.config.Configuration;

/**
 *
 * @author iconmaster
 */
public abstract class ConfigHandler<T> {

	public String modName;
	public EquivHandler<T> handler;
	public Map<ItemData, T> values;
	public File configDir;

	public boolean useCache = true;

	public ConfigHandler(String modName, EquivHandler<T> handler) {
		this.modName = modName;
		this.handler = handler;
	}

	public abstract String toString(T item);

	public abstract T fromString(String item);
	
	public void loadConfig(File file) {
		configDir = file;
		loadSettings(new File(file, "settings.cfg"));
		loadCache(new File(file, "cache.eqlib"));
	}

	public void loadSettings(File file) {
		Configuration cfg = new Configuration(file);
		cfg.load();
		cfg.setCategoryComment("general", "The equivalence settings for " + modName + ".");
		useCache = cfg.getBoolean("useCache", "general", useCache, "Set to false to disable caching. Just delete cache.eqlib to regenerate it once.");
		cfg.save();
	}

	public void loadCache(File file) {
		if (useCache) {
			if (file.exists()) {
				//load values from file
				try {
					Scanner in = new Scanner(file);
					values = stringToValues(EquivLib.readAll(in));
					//System.out.println(values);
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			} else {
				RecipeMap map = new RecipeMap(handler);
				map.generateNodes();
				values = map.getMap();
				try {
					file.createNewFile();
					PrintWriter pw = new PrintWriter(file);
					pw.print(valuesToString());
					pw.flush();
					pw.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		} else {
			RecipeMap map = new RecipeMap(handler);
			map.generateNodes();
			values = map.getMap();
		}
	}

	public String valuesToString() {
		StringBuilder sb = new StringBuilder();
		for (Map.Entry entry : values.entrySet()) {
			sb.append(ItemData.toString((ItemData) entry.getKey()));
			sb.append('=');
			sb.append(toString((T) entry.getValue()));
			sb.append('\n');
		}
		return sb.toString();
	}

	public Map<ItemData, T> stringToValues(String str) {
		HashMap<ItemData, T> map = new HashMap<ItemData, T>();
		String[] lines = str.split("\n");
		for (String line : lines) {
			String[] subs = line.split("=");
			if (subs.length==1) {
				if (!line.isEmpty() && !line.startsWith("#")) {
					//ERROR
				}
			} else {
				ItemData item = ItemData.fromString(subs[0]);
				T t = fromString(subs[1]);
				map.put(item, t);
			}
		}
		return map;
	}
}
