package xu.main.java.extractor;

import java.util.List;
import java.util.Map;

import xu.main.java.model.HtmlPath;

public class XpathExtractor implements IExtractor {

	public Map<String, String> extractorColumns(String html, List<HtmlPath> pathList) {

		for (@SuppressWarnings("unused") HtmlPath htmlPath : pathList) {
			// XElements xElements = Xsoup.select(html,
			// htmlPath.getDirPathList());
			// xElements.get();
		}

		return null;
	}

}
