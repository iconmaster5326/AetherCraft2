package com.iconmaster.eqlib.config;

import java.util.HashMap;

/**
 *
 * @author iconmaster
 */
public class ConfigRegistry {
	public static HashMap<String,ConfigHandler> handlers = new HashMap<String, ConfigHandler>();
	
	public static void registerConfig(ConfigHandler handler) {
		handlers.put(handler.modName, handler);
	}
}
