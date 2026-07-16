package com.mbm.filelogging;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class FileLogManager {

	private FileLogManager() {

	}

	private static ThreadLocal<BufferedWriter> threadLog = new ThreadLocal<>();

	public static void init(String testName) {
		try {
			String filePath = "logs/" + testName + ".log";
			BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true));
			threadLog.set(writer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static BufferedWriter getWriter() {
		return threadLog.get();
	}

	public static void close() {
		try {
			BufferedWriter writer = threadLog.get();
			if (writer != null) {
				writer.flush();
				writer.close();
				threadLog.remove(); // important to avoid memory leaks
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void write(String message) {
		try {
			BufferedWriter writer = getWriter();
			if (writer != null) {
				writer.write(message + "\n");
				writer.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
