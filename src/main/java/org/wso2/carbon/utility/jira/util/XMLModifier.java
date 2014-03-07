package org.wso2.carbon.utility.jira.util;

/**
 * this class is a utility class for modify the response
 * Created by pubudu on 2/12/14.
 */
public class XMLModifier {

    /**
     * This method will remove 'null' string from the response
     * @param xml
     * @return xml string
     */
    public static String modifyXML(String xml){
        xml = xml.replace("null","");
        return xml ;

    }
}
