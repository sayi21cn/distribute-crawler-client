package xu.main.java.distribute_crawler_client.extractor;

import java.util.List;
import java.util.Map;

import xu.main.java.distribute_crawler_common.vo.HtmlPath;

public interface IExtractor {
	public Map<String, String> extractorColumns(String html, List<HtmlPath> pathList);
}
