package org.cnc.qrcode.common;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import android.util.Log;

public class XMLfunctions {

	public static String getXML(String url) {
		String line = null;
		try {
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(url);
			HttpResponse httpResponse = httpClient.execute(httpPost);
			HttpEntity httpEntity = httpResponse.getEntity();
			line = EntityUtils.toString(httpEntity);
		} catch (Exception e) {
			line = null;
		}
		return line;
	}

	public final static Document XMLfromString(String xml) {
		Document doc = null;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(xml));
			doc = db.parse(is);
		} catch (Exception e) {
			return null;
		}

		return doc;
	}

	public final static String getElementValue(Node elem) {
		Node kid;
		if (elem != null) {
			if (elem.hasChildNodes()) {
				for (kid = elem.getFirstChild(); kid != null; kid = kid
						.getNextSibling()) {
					if (kid.getNodeType() == Node.TEXT_NODE) {
						return kid.getNodeValue();
					}
				}
			}
		}
		return "";
	}

	public static int numResults(Document doc) {
		Node results = doc.getDocumentElement();
		int res = -1;
		try {
			res = Integer.valueOf(results.getAttributes().getNamedItem("count")
					.getNodeValue());
		} catch (Exception e) {
			res = -1;
		}
		return res;
	}

	public static String getValue(Element item, String str) {
		try {
			NodeList n = item.getElementsByTagName(str);
			return XMLfunctions.getElementValue(n.item(0));
		} catch (Exception e) {
			return null;
		}
	}

	public static List<String> search(String key, String device_id) {
		List<String> _return = new ArrayList<String>();
		String url = Common.API10.replace("{0}", key) + "&device=" + device_id;
		Log.i("AAAA", url);
		String strXml = XMLfunctions.getXML(url);

		if (strXml != null) {
			Document document = XMLfunctions.XMLfromString(strXml);
			if (document != null) {
				_return.add(getValue(document, "status"));
				_return.add(getValue(document, "image"));
			}
		}
		return _return;
	}

	private static String getValue(Document document, String key) {
		try {
			return document.getElementsByTagName(key).item(0).getFirstChild()
					.getNodeValue();
		} catch (Exception e) {
			return null;
		}
	}

}
