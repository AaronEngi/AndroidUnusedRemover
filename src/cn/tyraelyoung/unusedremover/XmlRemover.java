package cn.tyraelyoung.unusedremover;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * @author wangchajiedian
 * @date 2015-12-16
 */

/**
 * 描述 使用dom接口删除xml节点
 */
public class XmlRemover {

	public static void remove(String path, String name, String tag) {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(path);
			NodeList nl = doc.getElementsByTagName(tag);
			for (int i = 0; i < nl.getLength(); i++) {
				Node nodeDel = nl.item(i);
				NamedNodeMap map = nodeDel.getAttributes();
				Node attNode = map.getNamedItem("name");
				String value = attNode.getNodeValue();
				if (name.equals(value)) {
					nodeDel.getParentNode().removeChild(nodeDel);
					System.out.println("removed:" + name);
					writeToXml(doc, path);
					return;
				}
			}
			System.out.println("not found:" + name);
			
			
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void writeToXml(Document doc, String rptdesign) {
		try {
			OutputStream fileoutputStream = new FileOutputStream(rptdesign);
			TransformerFactory tFactory = TransformerFactory.newInstance();
			Transformer transformer = tFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(fileoutputStream);
			transformer.transform(source, result);

		} catch (Exception e) {
			// toPrint("Can't write to file: "+ rptdesign);
			return;

		}
	}

}
