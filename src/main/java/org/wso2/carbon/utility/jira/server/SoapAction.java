package org.wso2.carbon.utility.jira.server;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.wso2.carbon.utility.jira.client.SoapClient;
import org.wso2.carbon.utility.jira.util.XMLModifier;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * @author Pubudu Dissanayake : pubudud@wso2.com on  2/12/14.
 */
public class SoapAction {

    SoapClient soapClient = null;
    String soapRespond = "";

    public String createProject(String username, String password,String soapURL,String projectKey,String name,String description,String url,String lead) {
        String token = null;
        soapClient = new SoapClient();
        try {
            token = getAuthToken(username,password,soapURL);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        String soapAction = "<soapenv:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:soap=\"http://soap.rpc.jira.atlassian.com\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\">\n" +
                "   <soapenv:Header/>\n" +
                "   <soapenv:Body>\n" +
                "      <soap:createProject soapenv:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">\n" +
                "         <in0 xsi:type=\"xsd:string\">"+token+"</in0>\n" +
                "         <in1 xsi:type=\"xsd:string\">"+projectKey+"</in1>\n" +
                "         <in2 xsi:type=\"xsd:string\">"+name+"</in2>\n" +
                "         <in3 xsi:type=\"xsd:string\">"+description+"</in3>\n" +
                "         <in4 xsi:type=\"xsd:string\">"+url+"</in4>\n" +
                "         <in5 xsi:type=\"xsd:string\">"+lead+"</in5>\n" +
                "         <in6 xsi:type=\"bean:RemotePermissionScheme\" xmlns:bean=\"http://beans.soap.rpc.jira.atlassian.com\">\n" +
                "            <description xsi:type=\"xsd:string\"></description>\n" +
                "            <id xsi:type=\"xsd:long\"></id>\n" +
                "            <name xsi:type=\"xsd:string\"></name>\n" +
                "            <type xsi:type=\"xsd:string\"></type>\n" +
                "            <permissionMappings xsi:type=\"jir:ArrayOf_tns1_RemotePermissionMapping\" soapenc:arrayType=\"bean:RemotePermissionMapping[]\" xmlns:jir=\"https://jirastg.wso2.org/jira/rpc/soap/jirasoapservice-v2\"/>\n" +
                "         </in6>\n" +
                "         <in7 xsi:type=\"bean:RemoteScheme\" xmlns:bean=\"http://beans.soap.rpc.jira.atlassian.com\">\n" +
                "            <description xsi:type=\"xsd:string\"></description>\n" +
                "            <id xsi:type=\"xsd:long\"></id>\n" +
                "            <name xsi:type=\"xsd:string\"></name>\n" +
                "            <type xsi:type=\"xsd:string\"></type>\n" +
                "         </in7>\n" +
                "         <in8 xsi:type=\"bean:RemoteScheme\" xmlns:bean=\"http://beans.soap.rpc.jira.atlassian.com\">\n" +
                "            <description xsi:type=\"xsd:string\">?</description>\n" +
                "            <id xsi:type=\"xsd:long\"></id>\n" +
                "            <name xsi:type=\"xsd:string\"></name>\n" +
                "            <type xsi:type=\"xsd:string\"></type>\n" +
                "         </in8>\n" +
                "      </soap:createProject>\n" +
                "   </soapenv:Body>\n" +
                "</soapenv:Envelope>";

        soapRespond= soapClient.sendMessage(soapURL,soapAction);
        return soapRespond;
    }

    public String login(String userName,String password){
        String soapAction = "<soapenv:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:soap=\"http://soap.rpc.jira.atlassian.com\">\n" +
                "   <soapenv:Header/>\n" +
                "   <soapenv:Body>\n" +
                "      <soap:login soapenv:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">\n" +
                "         <in0 xsi:type=\"xsd:string\">"+userName+"</in0>\n" +
                "         <in1 xsi:type=\"xsd:string\">"+password+"</in1>\n" +
                "      </soap:login>\n" +
                "   </soapenv:Body>\n" +
                "</soapenv:Envelope>";
        return soapAction;
    }

    public String getAuthToken(String username, String password,String soapURL) throws IOException, SAXException {

        String token ="";
        soapClient = new SoapClient();
        String respondMessage = soapClient.sendMessage(soapURL,login(username, password)) ;
        respondMessage = XMLModifier.modifyXML(respondMessage);
        DocumentBuilderFactory builderFactory =
                DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
            builder = builderFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        Document xmlDocument = null;
        if (builder != null) {
            xmlDocument = builder.parse(new ByteArrayInputStream(respondMessage.getBytes()));
        }
        NodeList nodes = xmlDocument.getElementsByTagName("loginReturn");
        token=nodes.item(0).getFirstChild().getNodeValue();


        return token;
    }
}
