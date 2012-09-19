package org.dynmap.web;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;

import org.dynmap.DynmapCore;
import org.dynmap.Log;

public class CustomBanIPFilter {
	private static String file_name = "banned-ips.txt";
	private static HashSet<String> banned_ips = new HashSet<String>();
	private static FileReader file = null;
	private static DynmapCore core = null;
	private static void loadFile() {
		file = null;
		banned_ips = null;
		try {
			file = new FileReader(core.getDataFolder() + "/" +file_name);
		} catch (FileNotFoundException e) {
			Log.severe("Error loading " + file_name);
		}
	}

	private static void loadData() {
		if (file == null) {
			return;
		}
		BufferedReader fis = null;
		try {
			fis = new BufferedReader(file);
			while (fis.ready()) {
				banned_ips.add(fis.readLine());
			}
		} catch (IOException iox) {
			Log.severe("Error reading " + file_name);
		} finally {
			if (fis != null)
				try {
					fis.close();
				} catch (IOException x) {
				}
		}
	}

	public static HashSet<String> getCustomIPs() {
		loadFile();
		loadData();
		return banned_ips;
	}
	
	public CustomBanIPFilter(DynmapCore core){
		File file = new File(core.getDataFolder(),file_name);
		CustomBanIPFilter.core = core;
		if(!file.exists()){
			try {
				file.createNewFile();
			} catch (IOException e) {
				Log.severe("Cannot create " + file_name);
			}
		}
	}

}

