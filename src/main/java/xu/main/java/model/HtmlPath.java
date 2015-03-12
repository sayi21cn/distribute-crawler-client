package xu.main.java.model;

import java.util.List;

public class HtmlPath {

	/* 抽取内容名称 */
	private String pathName;

	// 先由列表路径定位到多个带抽取节点，再由定位路径抽取

	/* 抽取列表路径 */
	private List<String> dirPathList;
	private List<Integer> dirIndexList;

	/* 抽取定位相对路径 */
	private List<String> pathList;
	private List<Integer> pathIndexList;

	/* 抽取属性 */
	private String attrName;

	private String attrValue;

	public String getPathName() {
		return pathName;
	}

	public void setPathName(String pathName) {
		this.pathName = pathName;
	}

	public List<String> getPathList() {
		return pathList;
	}

	public void setPathList(List<String> pathList) {
		this.pathList = pathList;
	}

	public List<String> getDirPathList() {
		return dirPathList;
	}

	public void setDirPathList(List<String> dirPathList) {
		this.dirPathList = dirPathList;
	}

	public List<Integer> getDirIndexList() {
		return dirIndexList;
	}

	public void setDirIndexList(List<Integer> dirIndexList) {
		this.dirIndexList = dirIndexList;
	}

	public List<Integer> getPathIndexList() {
		return pathIndexList;
	}

	public void setPathIndexList(List<Integer> pathIndexList) {
		this.pathIndexList = pathIndexList;
	}

	public String getAttrName() {
		return attrName;
	}

	public void setAttrName(String attrName) {
		this.attrName = attrName;
	}

	public String getAttrValue() {
		return attrValue;
	}

	public void setAttrValue(String attrValue) {
		this.attrValue = attrValue;
	}

}
