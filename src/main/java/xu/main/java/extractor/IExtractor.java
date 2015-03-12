package xu.main.java.extractor;

import java.util.List;
import java.util.Map;

import xu.main.java.model.HtmlPath;

public interface IExtractor {
	public Map<String, String> extractorColumns(String html, List<HtmlPath> pathList);
}
