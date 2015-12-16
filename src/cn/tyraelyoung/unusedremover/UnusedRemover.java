package cn.tyraelyoung.unusedremover;
/**
 * http://developer.android.com/training/basics/network-ops/xml.html#choose
 * @author wangchao
 * @date 2015-12-14
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

/**
 * 描述
 *TODO 
 *1. 多个location
 *    <issue
        id="UnusedResources"
        severity="Warning"
        message="The resource `R.string.ssdk_weibo_oauth_regiseter` appears to be unused"
        category="Performance"
        priority="3"
        summary="Unused resources"
        explanation="Unused resources make applications larger and slow down builds."
        errorLine1="    &lt;string name=&quot;ssdk_weibo_oauth_regiseter&quot;>应用授权&lt;/string>
"
        errorLine2="            ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~">
        <location
            file="F:\trunk1030\CSmall\src\main\res\values\ssdk_strings.xml"
            line="3"
            column="13"/>
        <location
            file="F:\trunk1030\CSmall\src\main\res\values-en\ssdk_strings.xml"
            line="3"
            column="13"/>
    </issue>
 * 
 * 2. 删除以后留下很多空行
 * 
 * 
 */
public class UnusedRemover {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new UnusedRemover().remove();

	}

	public void remove() {

		List<ItemUnused> list = new LintParser().parse();
		for (ItemUnused issueUnused : list) {
			switch (issueUnused.type) {
			case ItemUnused.TYPE_FILE:
				// layout 文件等
				// 直接删除文件。
				if (new File(issueUnused.location).delete()) {
					System.out.println("removed:" + issueUnused.location);
				}
				break;
			case ItemUnused.TYPE_COLOR:
				// string和color不能删除整个文件。
				// 用dom解析，删除对应xml节点
					XmlRemover.remove(issueUnused.location,
							issueUnused.resName, "color");
					break;
			case ItemUnused.TYPE_ARRAY:
				XmlRemover.remove(issueUnused.location,
						issueUnused.resName, "string-array");
				break;
			case ItemUnused.TYPE_STRING:
				XmlRemover.remove(issueUnused.location,
						issueUnused.resName, "string");
				break;
			case ItemUnused.TYPE_DIMEN:
				XmlRemover.remove(issueUnused.location,
						issueUnused.resName, "dimen");
				break;
				
			case ItemUnused.TYPE_NOT_DEFINED:
				break;
			}

		}
		System.out.print("ok");
	}
}
