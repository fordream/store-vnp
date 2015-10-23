package org.cnc.qrcode.adapter;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.cnc.qrcode.common.Answer;
import org.cnc.qrcode.common.Common;
import org.cnc.qrcode.common._Return;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.util.Log;

public class RequestServerAdapter {

	private String url;

	public RequestServerAdapter(String url) {
		this.url = url;
		Log.i("URL", url + "");
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	private InputStream getXMLFromUrl(String link) throws IOException {
		InputStream is = null;
		try {
			URL myurl = new URL(link);
			URLConnection conn = myurl.openConnection();
			is = conn.getInputStream();
			return is;
		} catch (Exception e) {
			e.printStackTrace();
			new Throwable(e.toString());
		}
		return is;
	}

	public Document parseXML(InputStream is) {
		Document doc = null;
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			doc = builder.parse(is);
			return doc;
		} catch (Exception e) {
			e.printStackTrace();
			new Throwable(e.toString());
		}
		return doc;
	}

	public NodeList getNodeListOfTagName(String tagName) {
		Document doc = null;
		NodeList nl = null;
		try {
			InputStream in = getXMLFromUrl(getUrl());
			doc = parseXML(in);
			nl = doc.getElementsByTagName(tagName).item(0).getChildNodes();
			return nl;
		} catch (IOException e) {
			e.printStackTrace();
			Log.d("Error in nodelist", "Error : " + e.toString());
			new Throwable(e.toString());
		}
		return nl;
	}

	// private NodeList getNoteList(Document doc, String tag) {
	// try {
	// return doc.getElementsByTagName(tag);
	// } catch (Exception e) {
	// return null;
	// }
	// }

	public ArrayList<String> parseXML() {
		ArrayList<String> result = new ArrayList<String>();

		Document dom = null;
		try {
			InputStream in = getXMLFromUrl(getUrl());
			dom = parseXML(in);
			String status = getData(dom, "success", 0);
			if ("1".equals(status)) {
				String type = getData(dom, "type", 0);
				result.add(type);
				if ("1".equals(type)) {
					result.add(getData(dom, "content", 0));
					result.add(getData(dom, "lng", 0));
					result.add(getData(dom, "lat", 0));
					return result;
				} else {
					result.add(getData(dom, "question", 0));
					String arrOfAnswer = "";
					if (dom.getElementsByTagName("answer") != null) {
						if (dom.getElementsByTagName("answer").item(0) != null) {
							arrOfAnswer = arrOfAnswer
									+ getData(dom, Common.ANSWRE, 0) + ",";

						}

						if (dom.getElementsByTagName("answer").item(1) != null) {
							arrOfAnswer = arrOfAnswer
									+ getData(dom, Common.ANSWRE, 1) + ",";

						}

						if (dom.getElementsByTagName("answer").item(2) != null) {
							arrOfAnswer = arrOfAnswer
									+ getData(dom, Common.ANSWRE, 2) + ",";

						}

						if (dom.getElementsByTagName("answer").item(3) != null) {
							arrOfAnswer = arrOfAnswer
									+ getData(dom, Common.ANSWRE, 3);

						}
					}
					result.add(arrOfAnswer);
					result.add(getData(dom, "answerid", 0));
					result.add(getData(dom, "nextpoint", 0));
					result.add(getData(dom, "lng", 0));
					result.add(getData(dom, "lat", 0));
					result.add(getData(dom, "content", 0));
					return result;
				}
			} else {
				// sucess = 0
				if (dom.getElementsByTagName("message").item(0).getFirstChild() != null) {
					result.add(getData(dom, "message", 0));
				}
				return result;
			}
		} catch (Throwable t) {

		}
		return result;
	}

	private String getData(Document doc, String tag, int index) {
		try {
			return doc.getElementsByTagName(tag).item(index).getFirstChild()
					.getNodeValue();
		} catch (Exception e) {
			return null;
		}
	}

	public ArrayList<String> parseXmlGps() {
		ArrayList<String> result = new ArrayList<String>();

		Document dom = null;
		try {
			InputStream in = getXMLFromUrl(getUrl());
			dom = parseXML(in);
			// xu ly xml tra ve
			// Node root = dom.getFirstChild();
			dom.getFirstChild();
			// get status return true or false
			String status = dom.getElementsByTagName("success").item(0)
					.getFirstChild().getNodeValue();
			result.add(status);
			String type = dom.getElementsByTagName("type").item(0)
					.getFirstChild().getNodeValue();
			result.add(type);
			// result.add(type);
			// neu dung vi tri thi se lay ve cau hoi va cac dap an hoac la thong
			// diep
			if (status.equals("1")) {
				// type = 2 is question and answer
				if (type.equals("0")) {
					// lay cau hoi position :3 (id=2)
					String question = dom.getElementsByTagName("question")
							.item(0).getFirstChild().getNodeValue();
					result.add(question);
					// lay danh sach cac cau tra loi position :4 (id=3)
					String arrOfAnswer = "";
					if (dom.getElementsByTagName("answer").item(0) != null) {
						arrOfAnswer = arrOfAnswer
								+ dom.getElementsByTagName("answer").item(0)
										.getFirstChild().getNodeValue() + ",";

					}
					if (dom.getElementsByTagName("answer").item(1) != null) {
						arrOfAnswer = arrOfAnswer
								+ dom.getElementsByTagName("answer").item(1)
										.getFirstChild().getNodeValue() + ",";

					}

					if (dom.getElementsByTagName("answer").item(2) != null) {
						arrOfAnswer = arrOfAnswer
								+ dom.getElementsByTagName("answer").item(2)
										.getFirstChild().getNodeValue() + ",";

					}

					if (dom.getElementsByTagName("answer").item(3) != null) {
						arrOfAnswer = arrOfAnswer
								+ dom.getElementsByTagName("answer").item(3)
										.getFirstChild().getNodeValue();

					}
					// answer 4 (id :3)
					result.add(arrOfAnswer);
					Log.d("xxx", "xxxqqq=" + arrOfAnswer);
					// lay id cua cau tra loi dung position :5 (id=4)
					String trueAnswer = dom.getElementsByTagName("answerid")
							.item(0).getFirstChild().getNodeValue();
					result.add(trueAnswer);
					// lay next point de kiem tra la ra thong bao (tra loi sai)
					// hay ra toa do posiotn : 6 (id=5)
					String nextPoint = dom.getElementsByTagName("nextpoint")
							.item(0).getFirstChild().getNodeValue();
					result.add(nextPoint);
					// lay id cua diem tiep theo position 6 (id = 5)
					// String makerId =
					// dom.getElementsByTagName("marker").item(0).getFirstChild().getNodeValue();
					// result.add(makerId);
					// neu next point = 1 thi tra ve toa do diem tiep theo
					// posiotn : 7 va 8 (id=6 va 7)
					String ltitude2 = dom.getElementsByTagName("lng").item(0)
							.getFirstChild().getNodeValue();
					String latitu2 = dom.getElementsByTagName("lat").item(0)
							.getFirstChild().getNodeValue();
					result.add(ltitude2);
					result.add(latitu2);
					// lay url (thong diep) position 9 (id = 8)
					String msgURL = dom.getElementsByTagName("content").item(0)
							.getFirstChild().getNodeValue();
					result.add(msgURL);
					return result;
				} else// type = 1 is message
				{
					// position 3 (id = 2)
					String messageOnly = dom.getElementsByTagName("content")
							.item(0).getFirstChild().getNodeValue();
					result.add(messageOnly);
					if (dom.getElementsByTagName("lng").item(0) != null) {
						String longtitude = dom.getElementsByTagName("lng")
								.item(0).getFirstChild().getNodeValue();
						result.add(longtitude);
					} else {
						result.add("0");
					}
					if (dom.getElementsByTagName("lat").item(0) != null) {
						String lattitude = dom.getElementsByTagName("lat")
								.item(0).getFirstChild().getNodeValue();
						result.add(lattitude);
					} else {
						result.add("0");
					}
					return result;
				}
			} else// lay thong diep bao chua dung vi tri
			{
				String message = dom.getElementsByTagName("content").item(0)
						.getFirstChild().getNodeValue();
				result.add(message);
				// lay longitude
				String lg = dom.getElementsByTagName("lng").item(0)
						.getFirstChild().getNodeValue();
				result.add(lg);
				// lay latitude
				String lte = dom.getElementsByTagName("lat").item(0)
						.getFirstChild().getNodeValue();
				Log.d("xxxx", "xxxey :" + lte);
				result.add(lte);
				return result;
				// return result;
			}
		} catch (Throwable t) {

		}
		// result.add("abc");
		return result;
	}

	public ArrayList<String> parseXmlFirst() {
		ArrayList<String> result = new ArrayList<String>();

		Document dom = null;
		try {
			InputStream in = getXMLFromUrl(getUrl());
			dom = parseXML(in);
			// xu ly xml tra ve
			// Node root = dom.getFirstChild();
			dom.getFirstChild();
			// get status return true or false
			String status = dom.getElementsByTagName("status").item(0)
					.getFirstChild().getNodeValue();
			result.add(status);
			if (status.equals("1")) {
				String image = dom.getElementsByTagName("image").item(0)
						.getFirstChild().getNodeValue();
				result.add(image);
			} else if (status.equals("0")) {
				String error = dom.getElementsByTagName("error").item(0)
						.getFirstChild().getNodeValue();
				result.add(error);
			}
			return result;
		} catch (Throwable t) {
		}
		return result;
	}

	public _Return parseXML1() {
		_Return result = new _Return();

		Document dom = null;
		try {
			InputStream in = getXMLFromUrl(getUrl());
			dom = parseXML(in);
			result.setAddress(getData(dom, "address", 0));
			result.setLat(getData(dom, "lat", 0));
			result.setLng(getData(dom, "lng", 0));
			result.setMessage(getData(dom, "message", 0));
			result.setName(getData(dom, "name", 0));
			result.setQuestion(getData(dom, "question", 0));
			result.setSuccess(getData(dom, "success", 0));
			result.setType(getData(dom, "type", 0));
			result.setUrl(getData(dom, "url", 0));
			result.setErrorCode(getData(dom, "errorCode", 0));
			result.setTimeStart(getData(dom, "timeStart", 0));
			result.setTimeEnd(getData(dom, "timeEnd", 0));

			result.lCommon.add(getData(dom, "success", 0));
			result.lCommon.add(getData(dom, "type", 0));
			result.lCommon.add(getData(dom, "message", 0));
			result.lCommon.add(getData(dom, "question", 0));
			result.lCommon.add(getData(dom, "name", 0));
			result.lCommon.add(getData(dom, "address", 0));
			// add nextPoint
			NodeList nodesnextPoint = dom.getElementsByTagName("nextPoint");
			String value[] = new String[2];
			value[0] = null;
			value[1] = null;
			if (nodesnextPoint != null) {
				Answer nextPoint = new Answer();
				for (int i = 0; i < nodesnextPoint.getLength(); i++) {
					Node node = nodesnextPoint.item(i);
					nextPoint.setLat(getData(node, 0));
					nextPoint.setLog(getData(node, 1));
					nextPoint.setName(getData(node, 2));
					nextPoint.setAddress(getData(node, 3));
					nextPoint.setUrl(getData(node, 4));
					value[0] = getData(node, 0);
					value[1] = getData(node, 1);
					if (i == 0) {
						break;
					}
				}
				result.setNextPoint(nextPoint);
			}

			// lat,lng of next Point
			result.lCommon.add(value[0]);
			result.lCommon.add(value[1]);

			result.lCommon.add(getData(dom, "lat", 0));
			result.lCommon.add(getData(dom, "lng", 0));

			NodeList nodes = dom.getElementsByTagName("answer");
			if (nodes != null) {
				for (int i = 0; i < nodes.getLength(); i++) {
					Node node = nodes.item(i);
					Answer answer2 = new Answer();
					answer2.setText(getData(node, 0));
					answer2.setLat(getData(node, 1));
					answer2.setLog(getData(node, 2));
					answer2.setName(getData(node, 3));
					answer2.setAddress(getData(node, 4));
					answer2.setUrl(getData(node, 5));
					result.lAnswre.add(answer2);
				}
			}
		} catch (Exception e) {
		}

		return result;
	}

	private String getData(Node node, int index) {
		try {
			return node.getChildNodes().item(index).getFirstChild()
					.getNodeValue();
		} catch (Exception e) {
			return null;
		}
	}

}
