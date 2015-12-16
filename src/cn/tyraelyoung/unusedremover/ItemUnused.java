package cn.tyraelyoung.unusedremover;
/**
 * @author wangchao
 * @date 2015-12-14
 */

/**
 * 描述
 */
public class ItemUnused {
	public static final int TYPE_NOT_DEFINED = 0;
	public static final int TYPE_FILE = 1;
	public static final int TYPE_COLOR = 2;
	public static final int TYPE_STRING = 3;
	public static final int TYPE_ARRAY = 4;
	public static final int TYPE_DIMEN = 5;

	/*
	 * 文件位置
	 */
	public String location;
	/**
	 * 资源名称
	 */
	public String resName;
	/**
	 * 资源类型
	 */
	public int type;

	public ItemUnused(String location) {
		super();
		this.location = location;
	}

}
