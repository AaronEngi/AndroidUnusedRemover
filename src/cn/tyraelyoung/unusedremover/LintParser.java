package cn.tyraelyoung.unusedremover;
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
 * @author wangchao
 * @date 2015-12-16
 */

/**
 * 使用xmlpull接口解析lint
 * 描述
 */
public class LintParser {
	private static final String ns = null;
	private static String mLintResult = "F:/trunk1030/CSmall/build/outputs/lint-results-devDebug.xml";
	
	public List<ItemUnused> parse() {
		FileInputStream inputStream;
		try {
			inputStream = new FileInputStream(mLintResult);
			return parse(inputStream);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}	
	
	public List<ItemUnused> parse(InputStream in) throws XmlPullParserException, IOException {
		
        try {
            XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            return readFeed(parser);
        } finally {
            in.close();
        }
    }
    
    private List readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
        List entries = new ArrayList();

        parser.require(XmlPullParser.START_TAG, ns, "issues");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            String id = parser.getAttributeValue(null, "id");
            String message = parser.getAttributeValue(null, "message");            
            
            if(name.equals("issue") && "UnusedResources".equals(id)){
            	//项目是未使用的         
            	ItemUnused issueUnused = readEntry(parser);
            	if(issueUnused == null){
            		
            	}else{
            		if(issueUnused.location.contains("\\values")){
                		if(message.contains("R.color.")){
                			issueUnused.type = ItemUnused.TYPE_COLOR;
                			issueUnused.resName = getColorName(message);
                			System.out.println("issueUnused.resName:" + issueUnused.resName);
                		}else if(message.contains("R.array.")){
                			issueUnused.type = ItemUnused.TYPE_ARRAY;
                			issueUnused.resName = getName("R.array.", message);
                			System.out.println("issueUnused.resName:" + issueUnused.resName);
                		}else if(message.contains("R.string.")){
                			issueUnused.type = ItemUnused.TYPE_STRING;
                			issueUnused.resName = getName("R.string.", message);
                			System.out.println("issueUnused.resName:" + issueUnused.resName);
                		}else if(message.contains("R.dimen.")){
                			issueUnused.type = ItemUnused.TYPE_DIMEN;
                			issueUnused.resName = getName("R.dimen.", message);
                			System.out.println("issueUnused.resName:" + issueUnused.resName);
                		}
            		}else{
            			issueUnused.type = ItemUnused.TYPE_FILE;
            		}
            		entries.add(issueUnused);            		
            	}
            }else{
            	skip(parser);
            }
            
        }  
        return entries;
    }
    
    /**
     * 读入文件位置、自选类型等。
     * @param parser
     * @return
     * @throws XmlPullParserException
     * @throws IOException
     */
    private ItemUnused readEntry(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "issue");
        String location = null;
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("location")) {
            	location = readLink(parser);   
            } else {
                skip(parser);
            }
        }
        return new ItemUnused(location);
    }
    
 // Processes link tags in the feed.
    private String readLink(XmlPullParser parser) throws IOException, XmlPullParserException {
    	parser.require(XmlPullParser.START_TAG, ns, "location");
            String relType = parser.getAttributeValue(null, "file");
            parser.nextTag();
            parser.require(XmlPullParser.END_TAG, ns, "location");
            return relType;
              
    }
    
    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
            case XmlPullParser.END_TAG:
                depth--;
                break;
            case XmlPullParser.START_TAG:
                depth++;
                break;
            }
        }
     }
    
    private String getColorName(String message){
    	int start = message.indexOf("R.color.") + 8;
    	int end = message.indexOf("`", start);
    	String color = message.substring(start, end);
    	return color;
    }
    
    private String getName(String type, String message){
    	int start = message.indexOf(type) + type.length();
    	int end = message.indexOf("`", start);
    	String color = message.substring(start, end);
    	return color;
    }
    
    
}
