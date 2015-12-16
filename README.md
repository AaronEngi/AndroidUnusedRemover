# AndroidUnusedRemover
用java代码实现的移除未使用资源。

1. 运行lint，例如：androidstudio的lint
2. 修改LintParser类的属性。
private static String mLintResult = "F:/trunk1030/CSmall/build/outputs/lint-results-devDebug.xml";

3. 运行UnusedRemver中的main方法。
public static void main(String[] args) {}


已知问题：
 *1. 多个location的情况只能清除一个。
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