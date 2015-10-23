/*
 * Copyright (C) 2008 ZXing authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.cnc.qrcode.benchmarkitem;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.cnc.qrcode.adapter.RequestServerAdapter;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.ReaderException;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;

public class BenchmarkThread extends Thread {

	private static final int RUNS = 10;
	public static String url_scan_from_lib;
	private String mPath;
	private MultiFormatReader mMultiFormatReader;
	@SuppressWarnings("unused")
	private RequestServerAdapter objRequest;

	public BenchmarkThread(String path) {
		// mActivity = activity;
		mPath = path;
	}

	@Override
	public void run() {
		mMultiFormatReader = new MultiFormatReader();
		mMultiFormatReader.setHints(null);
		// Try to get in a known state before starting the benchmark
		System.gc();

		List<BenchmarkItem> items = new ArrayList<BenchmarkItem>();
		// String link = walkTree(mPath, items);
		walkTree(mPath, items);
	}

	public String getLink() {
		mMultiFormatReader = new MultiFormatReader();
		mMultiFormatReader.setHints(null);
		System.gc();
		List<BenchmarkItem> items = new ArrayList<BenchmarkItem>();
		String link = walkTree(mPath, items);
		return link;
	}

	private String walkTree(String path, List<BenchmarkItem> items) {
		String item = "";
		File file = new File(path);
		if (file.isDirectory()) {
			String[] files = file.list();
			Arrays.sort(files);
			for (int x = 0; x < files.length; x++) {
				walkTree(file.getAbsolutePath() + '/' + files[x], items);
			}
		} else {
			item = decode(path);
		}
		return item;
	}

	@SuppressWarnings("unused")
	private String decode(String path) {
		RGBLuminanceSource source;
		try {
			source = new RGBLuminanceSource(path);
		} catch (FileNotFoundException e) {
			return null;
		}

		BenchmarkItem item = new BenchmarkItem(path, RUNS);
		boolean success;
		Result result = null;
		try {
			BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
			result = mMultiFormatReader.decodeWithState(bitmap);
			success = true;
			url_scan_from_lib = result.getText();
			return url_scan_from_lib;
		} catch (ReaderException e) {
			success = false;
		}

		return url_scan_from_lib;
	}

	public String getURL() {
		return BenchmarkThread.url_scan_from_lib;
	}
}
