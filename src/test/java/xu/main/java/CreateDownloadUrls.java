package xu.main.java;

public class CreateDownloadUrls {

	public static void createDYTTUrls() {
		String urlPattern = "http://www.ygdy8.net/html/gndy/dyzz/list_23_%s.html";
		for (int pageNo = 1; pageNo <= 152; pageNo++) {
			System.out.println(String.format(urlPattern, pageNo));
		}
	}

	public static void main(String[] args) {
		createDYTTUrls();
	}

}
