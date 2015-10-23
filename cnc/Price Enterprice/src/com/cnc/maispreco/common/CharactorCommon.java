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
			value = value.replace("&Agrave;", "À");
			value = value.replace("&Aacute;", "Á");
			value = value.replace("&Acirc;", "Â");
			value = value.replace("&Atilde;", "Ã");
			value = value.replace("&Auml;", "Ä");
			value = value.replace("&Aring;", "Å");
			value = value.replace("&AElig;", "Æ");
			value = value.replace("&Ccedil;", "Ç");
			value = value.replace("&Egrave;", "È");
			value = value.replace("&Eacute;", "É");
			value = value.replace("&Ecirc;", "Ê");
			value = value.replace("&Euml;", "Ë");
			value = value.replace("&Igrave;", "Ì");
			value = value.replace("&Iacute;", "Í");
			value = value.replace("&Icirc;", "Î");
			value = value.replace("&Iuml;", "Ï");
			value = value.replace("&ETH;", "Ð");
			value = value.replace("&Ntilde;", "Ñ");
			value = value.replace("&Ograve;", "Ò");
			value = value.replace("&Oacute;", "Ó");
			value = value.replace("&Ocirc;", "Ô");
			value = value.replace("&Otilde;", "Õ");
			value = value.replace("&Ouml;", "Ö");
			value = value.replace("&Oslash;", "Ø");
			value = value.replace("&Ugrave;", "Ù");
			value = value.replace("&Uacute;", "Ú");
			value = value.replace("&Ucirc;", "Û");
			value = value.replace("&Uuml;", "Ü");
			value = value.replace("&Yacute;", "Ý");
			value = value.replace("&THORN;", "Þ");
			value = value.replace("&szlig;", "ß");
			value = value.replace("&agrave;", "à");
			value = value.replace("&aacute;", "á");
			value = value.replace("&acirc;", "â");
			value = value.replace("&atilde;", "ã");
			value = value.replace("&auml;", "ä");
			value = value.replace("&aring;", "å");
			value = value.replace("&aelig;", "æ");
			value = value.replace("&ccedil;", "ç");
			value = value.replace("&egrave;", "è");
			value = value.replace("&eacute;", "é");
			value = value.replace("&ecirc;", "ê");
			value = value.replace("&euml;", "ë");
			value = value.replace("&igrave;", "ì");
			value = value.replace("&iacute;", "í");
			value = value.replace("&icirc;", "î");
			value = value.replace("&iuml;", "ï");
			value = value.replace("&eth;", "ð");
			value = value.replace("&ntilde;", "ñ");
			value = value.replace("&ograve;", "ò");
			value = value.replace("&oacute;", "ó");
			value = value.replace("&ocirc;", "ô");
			value = value.replace("&otilde;", "õ");
			value = value.replace("&ouml;", "ö");
			value = value.replace("&oslash;", "ø");
			value = value.replace("&ugrave;", "ù");
			value = value.replace("&uacute;", "ú");
			value = value.replace("&ucirc;", "û");
			value = value.replace("&uuml;", "ü");
			value = value.replace("&yacute;", "ý");
			value = value.replace("&thorn;", "þ");
			value = value.replace("&yuml;", "ÿ");
		}

		return value;
	}
}
