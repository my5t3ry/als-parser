package de.my5t3ry.als_parser.utils;

import org.w3c.dom.Document;

import javax.xml.namespace.QName;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

/**
 * created by: sascha.bast
 * since: 09.09.17
 */
public class XPathEvaluator {
    
    private static final String DTM_MANAGER_NAME = "com.sun.org.apache.xml.internal.dtm.DTMManager";
    private static final String DTM_MANAGER_VALUE = "com.sun.org.apache.xml.internal.dtm.ref.DTMManagerDefault";

    static {
        System.setProperty(DTM_MANAGER_NAME, DTM_MANAGER_VALUE);
    }

    private static final ThreadLocal<XPathFactory> XPATH_FACTORY = ThreadLocal.withInitial(() -> XPathFactory.newInstance());

    public static Object query(String xPathExpression, Document document, QName resultType) {
        try {
            XPath xpath = XPATH_FACTORY.get().newXPath();
            XPathExpression expression = xpath.compile(xPathExpression);
            return expression.evaluate(document, resultType);
        } catch (XPathExpressionException e) {
            throw new IllegalStateException("Error while executing XPath evaluation!", e);
        }
    }
}
