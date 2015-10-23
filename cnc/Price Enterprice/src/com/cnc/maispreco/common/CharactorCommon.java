package com.cnc.maispreco.common;

public class CharactorCommon {
	public static String convert(String value) {

		if (value != null) {
			value = value.replace("&nbsp;", " ");
			value = value.replace("&lt;", "<");
			value = value.replace("&gt;", ">");
			value = value.replace("&amp;", "&");
			value = value.replace("&quot;", "\"");
			value = value.replace("&apos;", "\'");
			value = value.replace("&Agrave;", "�");
			value = value.replace("&Aacute;", "�");
			value = value.replace("&Acirc;", "�");
			value = value.replace("&Atilde;", "�");
			value = value.replace("&Auml;", "�");
			value = value.replace("&Aring;", "�");
			value = value.replace("&AElig;", "�");
			value = value.replace("&Ccedil;", "�");
			value = value.replace("&Egrave;", "�");
			value = value.replace("&Eacute;", "�");
			value = value.replace("&Ecirc;", "�");
			value = value.replace("&Euml;", "�");
			value = value.replace("&Igrave;", "�");
			value = value.replace("&Iacute;", "�");
			value = value.replace("&Icirc;", "�");
			value = value.replace("&Iuml;", "�");
			value = value.replace("&ETH;", "�");
			value = value.replace("&Ntilde;", "�");
			value = value.replace("&Ograve;", "�");
			value = value.replace("&Oacute;", "�");
			value = value.replace("&Ocirc;", "�");
			value = value.replace("&Otilde;", "�");
			value = value.replace("&Ouml;", "�");
			value = value.replace("&Oslash;", "�");
			value = value.replace("&Ugrave;", "�");
			value = value.replace("&Uacute;", "�");
			value = value.replace("&Ucirc;", "�");
			value = value.replace("&Uuml;", "�");
			value = value.replace("&Yacute;", "�");
			value = value.replace("&THORN;", "�");
			value = value.replace("&szlig;", "�");
			value = value.replace("&agrave;", "�");
			value = value.replace("&aacute;", "�");
			value = value.replace("&acirc;", "�");
			value = value.replace("&atilde;", "�");
			value = value.replace("&auml;", "�");
			value = value.replace("&aring;", "�");
			value = value.replace("&aelig;", "�");
			value = value.replace("&ccedil;", "�");
			value = value.replace("&egrave;", "�");
			value = value.replace("&eacute;", "�");
			value = value.replace("&ecirc;", "�");
			value = value.replace("&euml;", "�");
			value = value.replace("&igrave;", "�");
			value = value.replace("&iacute;", "�");
			value = value.replace("&icirc;", "�");
			value = value.replace("&iuml;", "�");
			value = value.replace("&eth;", "�");
			value = value.replace("&ntilde;", "�");
			value = value.replace("&ograve;", "�");
			value = value.replace("&oacute;", "�");
			value = value.replace("&ocirc;", "�");
			value = value.replace("&otilde;", "�");
			value = value.replace("&ouml;", "�");
			value = value.replace("&oslash;", "�");
			value = value.replace("&ugrave;", "�");
			value = value.replace("&uacute;", "�");
			value = value.replace("&ucirc;", "�");
			value = value.replace("&uuml;", "�");
			value = value.replace("&yacute;", "�");
			value = value.replace("&thorn;", "�");
			value = value.replace("&yuml;", "�");
		}

		return value;
	}
}
