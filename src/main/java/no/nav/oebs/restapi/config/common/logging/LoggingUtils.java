package no.nav.oebs.restapi.config.common.logging;

import java.util.regex.Pattern;

public class LoggingUtils {

	private static final Pattern FNR_PATTERN = Pattern.compile("(\\D+|^)(\\d{2})\\d{7}(\\d{2})(\\D+|$)");

	private LoggingUtils() {
	}

	public static String maskIfFnr(String text) {
		if (text == null) {
			return "(null)";
		}
		return FNR_PATTERN.matcher(text).replaceAll("$1$2" + "*******" + "$3$4");
	}
}
