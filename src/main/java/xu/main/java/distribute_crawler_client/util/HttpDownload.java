package xu.main.java.distribute_crawler_client.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class HttpDownload {

	public static String download(String url,String charSet) {
		
		InputStream in = null;
        try {
            in = new URL(url).openConnection().getInputStream();
            byte[] inputArray = readAll(in);
            return new String(inputArray, charSet);
        } catch (Exception e) {
           e.printStackTrace();
        }
        
		
		return "success";
	}
	/**
     * 从输入流中读取所有字节
     *
     * @param in 输入流
     * @return 字节数组
     */
    public static byte[] readAll(InputStream in) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            byte[] buffer = new byte[4096];
            for (int n; (n = in.read(buffer)) > 0;) {
                out.write(buffer, 0, n);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return out.toByteArray();
    }
}
