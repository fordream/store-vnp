package org.cnc.qrcode.config;

public class Config {
	public String errorkey;
	public String start;
	public String end;
	private static Config config = new Config();

	public static Config getInstance() {
		return config;
	}

	private Config() {
	}

	public void reset() {
		errorkey = null;
		start = null;
		end = null;
	}
}