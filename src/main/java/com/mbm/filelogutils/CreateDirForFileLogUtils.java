package com.mbm.filelogutils;

import java.io.File;

public class CreateDirForFileLogUtils {

	public static void cleanPreviousLogs(String logFolderPath) {
		File logDir = new File(logFolderPath);
		if (logDir.exists()) {
			deleteFolderRecursively(logDir);
		}
		logDir.mkdirs(); // create new folder for current run
	}

	private static void deleteFolderRecursively(File folder) {
		File[] files = folder.listFiles();
		if (files != null) {
			for (File f : files) {
				if (f.isDirectory()) {
					deleteFolderRecursively(f);
				} else {
					f.delete();
				}
			}
		}
		folder.delete();
	}
}
