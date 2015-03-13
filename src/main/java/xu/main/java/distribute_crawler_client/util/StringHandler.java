package xu.main.java.distribute_crawler_client.util;

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
