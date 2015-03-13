package xu.main.java.extractor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import xu.main.java.config.DbConfig;
import xu.main.java.distribute_crawler_common.vo.HtmlPath;
import xu.main.java.util.StringHandler;

public class CssExtractor implements IExtractor {
	
	public Map<String, String> extractorColumns(String html, List<HtmlPath> pathList) {
		Map<String, String> resultMap = new HashMap<String, String>();

		if (StringHandler.isNullOrEmpty(html)) {
			return resultMap;
		}

		Document document = Jsoup.parse(html);
		for (HtmlPath cssPath : pathList) {
			Elements elements = document.getAllElements();
			StringBuffer resultBuffer = new StringBuffer();
			if (null != cssPath.getDirPathList()) {
				elements = findElements(document, cssPath.getDirPathList(), cssPath.getDirIndexList());
			}
			for (int index = 0, len = elements.size(); index < len; index++) {
				Element element = elements.get(index);
				element = findElement(element, cssPath.getPathList(), cssPath.getPathIndexList());
				if (StringHandler.isNullOrEmpty(cssPath.getAttrName())) {
					resultBuffer.append(element.text()).append(DbConfig.SPLIT_STRING);
					continue;
				}
				resultBuffer.append(element.attr(cssPath.getAttrName())).append(DbConfig.SPLIT_STRING);
			}
			resultMap.put(cssPath.getPathName(), resultBuffer.toString());
		}
		return resultMap;
	}

	public static Elements findElements(Element element, List<String> pathList, List<Integer> elementIndexList) {
		if (null == pathList || pathList.size() < 1 || pathList.size() > elementIndexList.size()) {
			return null;
		}
		Element element1 = element;
		Elements elements = element.getAllElements();
		for (int pathIndex = 0, len = pathList.size(); pathIndex < len; pathIndex++) {
			String path = pathList.get(pathIndex);
			int elementIndex = elementIndexList.get(pathIndex);
			elements = element1.select(path);
			if (elements.size() <= elementIndex) {
				System.out.println("路径错误 " + path + " 获取组件数 : " + elements.size() + " 要抽取的组件索引: " + elementIndex);
				return null;
			}
			element1 = elements.get(elementIndex);
		}
		return elements;
	}

	public static Element findElement(Element element, List<String> pathList, List<Integer> elementIndexList) {

		if (null == pathList || pathList.size() < 1 || pathList.size() != elementIndexList.size()) {
			return null;
		}
		// Element element1 = element;
		// for (int pathIndex = 0, len = pathList.size(); pathIndex < len;
		// pathIndex++) {
		// String path = pathList.get(pathIndex);
		// int elementIndex = elementIndexList.get(pathIndex);
		// Elements elements = element1.select(path);
		// if (elements.size() <= elementIndex) {
		// System.out.println("路径错误 " + path + " 获取组件数 : " + elements.size() +
		// " 要抽取的组件索引: " + elementIndex);
		// return null;
		// }
		// element1 = elements.get(elementIndex);
		// }
		Elements elements = findElements(element, pathList, elementIndexList);
		return elements.get(elementIndexList.get(pathList.size() - 1));
	}

	public static void main(String[] args) {
		String html = "<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"tbspan\" style=\"margin-top:6px\"><tbody><tr> <td height=\"1\" colspan=\"2\" background=\"/templets/img/dot_hor.gif\"></td></tr><tr> <td width=\"5%\" height=\"26\" align=\"center\"><img src=\"/templets/img/item.gif\" width=\"18\" height=\"17\"></td><td height=\"26\">	<b>				<a href=\"/html/gndy/dyzz/20150212/47357.html\" class=\"ulink\">2014年爱情喜剧《单身男女2》BD国粤双语中字</a>	</b></td></tr><tr> <td height=\"20\" style=\"padding-left:3px\">&nbsp;</td><td style=\"padding-left:3px\">				<font color=\"#8F8C89\">日期：2015-02-12 23:10:09 点击：0 </font>						</td></tr><tr> <td colspan=\"2\" style=\"padding-left:3px\">◎又 名 单身男女2 ◎片 名 Don't Go Breaking My Heart ◎年 代 2014 ◎国 家 中国香港/中国 ◎类 别 喜剧/爱情 ◎语 言 普通话/粤语 ◎字 幕 中文 ◎IMDB评分 5.2/10 from 108 users ◎文件格式 x264 + AAC ◎视频尺寸 1280 x 720 ◎文件大� �1CD ◎片 长 113 Mins</td></tr></tbody></table>";
		Document document = Jsoup.parse(html);
		List<String> pathList = Arrays.asList("table", "tbody", "tr", "td", "b", "a");
		List<Integer> elementIndexList = Arrays.asList(0, 0, 1, 1, 0, 0);
		Element element = findElement(document, pathList, elementIndexList);
		System.out.println(element.text());
		System.out.println(element.attr("href"));
	}

}
