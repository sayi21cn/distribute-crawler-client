package xu.main.java;

import org.junit.Test;

public class CreateDownloadUrls {

	@Test
	public void testCreateDYTTUrls() {
		String urlPattern = "http://www.ygdy8.net/html/gndy/dyzz/list_23_%s.html";
		for (int pageNo = 1; pageNo <= 152; pageNo++) {
			System.out.println(String.format(urlPattern, pageNo));
		}
	}

}
