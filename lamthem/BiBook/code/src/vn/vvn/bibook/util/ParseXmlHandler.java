/**
 * 
 */
package vn.vvn.bibook.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import vn.vvn.bibook.item.Book;

import android.util.Log;


/**
 * @author haipn
 * 
 */
public class ParseXmlHandler extends DefaultHandler {
	private String TAG = "XMLParser";
	private boolean in_root = false;
	private boolean in_title = false;
	private boolean in_description = false;
	private boolean in_author = false;
	private boolean in_number_pages = false;
	private boolean in_categories = false;
	private Book mData;

	public Book getParseData() {
		return this.mData;
	}

	@Override
	public void startDocument() throws SAXException {
		this.mData = new Book();
	}

	@Override
	public void endDocument() throws SAXException {
		// Nothing to do
	}

	/**
	 * Gets be called on opening tags like: <tag> Can provide attribute(s), when
	 * xml was like: <tag attribute="attributeValue">
	 */
	@Override
	public void startElement(String namespaceURI, String localName,
			String qName, Attributes atts) throws SAXException {
		if (localName.equals("root")) {
			this.in_root = true;
		} else if (localName.equals("title")) {
			this.in_title = true;
		} else if (localName.equals("description")) {
			this.in_description = true;
		} else if (localName.equals("author")) {
			this.in_author = true;
		} else if (localName.equals("number_pages")) {
			this.in_number_pages = true;
		} else if (localName.equals("categories")) {
			this.in_categories = true;
		}
	}

	/**
	 * Gets be called on closing tags like: </tag>
	 */
	@Override
	public void endElement(String namespaceURI, String localName, String qName)
			throws SAXException {
	}

	/**
	 * Gets be called on the following structure: <tag>characters</tag>
	 */
	@Override
	public void characters(char ch[], int start, int length) {
		String temp = new String(ch, start, length);
		// if (temp != "\0") {
		try {
			if (this.in_title) {
				Log.d("haipn", "title: " + temp);
				mData.setTitle(URLDecoder.decode(temp, "UTF-8"));
				in_title = false;
			}
			if (this.in_description) {
				Log.d("haipn", "description: " + temp);
				mData.setDescription(URLDecoder.decode(temp, "UTF-8"));
				this.in_description = false;
			}
			if (this.in_categories) {
				Log.d("haipn", "Categories: " + temp);
				mData.setCategories(URLEncoder.encode(temp, "UTF-8"));
				this.in_categories = false;
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
