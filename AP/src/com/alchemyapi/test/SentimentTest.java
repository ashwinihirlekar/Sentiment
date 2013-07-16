package com.alchemyapi.test;

import com.alchemyapi.api.AlchemyAPI;
import org.xml.sax.SAXException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

class SentimentTest {
    public static void main(String[] args) throws IOException, SAXException,
            ParserConfigurationException, XPathExpressionException {
               
        AlchemyAPI alchemyObj = AlchemyAPI.GetInstanceFromString("861ca2d8bcc2df596e0838943fe23333fc33c3c8");

        Document doc = alchemyObj.TextGetTextSentiment("That hat is nice , Charles.");
        System.out.println("Sentitments from Text ---------"+getStringFromDocument(doc));
          
       
        NodeList nodes = doc.getElementsByTagName("results");
        for (int i = 0; i < nodes.getLength(); i++) {
        	Node node = nodes.item(i);

        	if (node.getNodeType() == Node.ELEMENT_NODE) 
        	{
        	Element element = (Element) node;
        	System.out.println("Sentiment :  " + getValue("type", element));
        	System.out.println("Score : " + getValue("score", element));
        	
        	}
        	}        	
    
       
        // Extract sentiment for a text string.
        /*doc = alchemyObj.TextGetTextSentiment(
            "That hat is ridiculous, Charles.");
        System.out.println(getStringFromDocument(doc));

        // Load a HTML document to analyze.
        //String htmlDoc = getFileContents("/home/bcard/data/example.html");
 
       String htmlDoc = getFileContents("D:/All Software/AlchemyAPI_Java-0.8/testdir/data/example.html");
        //String htmlDoc = getFileContents("/testdir/data/example.html");
        
        // Extract sentiment for a HTML document.
        doc = alchemyObj.HTMLGetTextSentiment(htmlDoc, "http://www.test.com/");
	System.out.println(getStringFromDocument(doc));
	
	// Extract entity-targeted sentiment from a HTML document.	
	AlchemyAPI_NamedEntityParams entityParams = new AlchemyAPI_NamedEntityParams();
	entityParams.setSentiment(true);
	doc = alchemyObj.TextGetRankedNamedEntities("That Mike Tyson is such a sweetheart.", entityParams);
	System.out.println(getStringFromDocument(doc));
	
	// Extract keyword-targeted sentiment from a HTML document.	
	AlchemyAPI_KeywordParams keywordParams = new AlchemyAPI_KeywordParams();
	keywordParams.setSentiment(true);
	doc = alchemyObj.TextGetRankedKeywords("That Mike Tyson is such a sweetheart.", keywordParams);
	System.out.println(getStringFromDocument(doc));
        
	//Extract Targeted Sentiment from text
	AlchemyAPI_TargetedSentimentParams sentimentParams = new AlchemyAPI_TargetedSentimentParams();
	sentimentParams.setShowSourceText(true);
	doc = alchemyObj.TextGetTargetedSentiment("This car is terrible.", "car", sentimentParams);
	System.out.print(getStringFromDocument(doc));

	//Extract Targeted Sentiment from url
	doc = alchemyObj.URLGetTargetedSentiment("http://techcrunch.com/2012/03/01/keen-on-anand-rajaraman-how-walmart-wants-to-leapfrog-over-amazon-tctv/", "Walmart",sentimentParams);
	System.out.print(getStringFromDocument(doc));

	//Extract Targeted Sentiment from html
	doc = alchemyObj.HTMLGetTargetedSentiment(htmlDoc, "http://www.test.com/", "WujWuj", sentimentParams);
	System.out.print(getStringFromDocument(doc));*/
}

    private static String getValue(String tag, Element element) {
    	NodeList nodes = element.getElementsByTagName(tag).item(0).getChildNodes();
    	Node node = (Node) nodes.item(0);
    	return node.getNodeValue();
    	}
    // utility function
    /*private static String getFileContents(String filename)
        throws IOException, FileNotFoundException
    {
    	
        File file = new File(filename);
        StringBuilder contents = new StringBuilder();

        BufferedReader input = new BufferedReader(new FileReader(file));

        try {
            String line = null;

            while ((line = input.readLine()) != null) {
                contents.append(line);
                contents.append(System.getProperty("line.separator"));
            }
        } finally {
            input.close();
        }

        return contents.toString();
    }
*/
    // utility method
    private static String getStringFromDocument(Document doc) {
        try {
            DOMSource domSource = new DOMSource(doc);
            StringWriter writer = new StringWriter();
            StreamResult result = new StreamResult(writer);

            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.transform(domSource, result);

            return writer.toString();
        } catch (TransformerException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
