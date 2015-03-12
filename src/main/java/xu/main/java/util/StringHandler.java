package xu.main.java.util;

public class StringHandler {

	public static boolean isNullOrEmpty(String input) {
		return null == input || "".equals(input);
	}

	public static String nullToEmpty(String input) {
		if (null == input) {
			return "";
		}
		return input;
	}

}
