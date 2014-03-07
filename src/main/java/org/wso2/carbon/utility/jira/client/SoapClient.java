package org.wso2.carbon.utility.jira.client;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by pubudu on 2/12/14.
 */
public class SoapClient {

    public String sendMessage(String serviceUrl, String messageBody) {
        URL url = null;
        URLConnection connection = null;
        HttpURLConnection httpConn = null;
        String responseString = null;
        String response = null;
        ByteArrayOutputStream bout = null;
        OutputStream out = null;
        InputStreamReader isr = null;
        BufferedReader in = null;
        try {
            url = new URL(serviceUrl);
            connection = url.openConnection();
            httpConn = (HttpURLConnection) connection;
            bout = new ByteArrayOutputStream();
            byte[] buffer = new byte[messageBody.length()];
            buffer = messageBody.getBytes();
            bout.write(buffer);

            byte[] b = bout.toByteArray();
            String SOAPAction = "";
            // Set the appropriate HTTP parameters.
            httpConn.setRequestProperty("Content-Length", String
                    .valueOf(b.length));
            httpConn.setRequestProperty("Content-Type",
                    "text/xml; charset=utf-8");
            httpConn.setRequestProperty("SOAPAction", SOAPAction);
            httpConn.setRequestMethod("GET");
            httpConn.setDoOutput(true);
            httpConn.setDoInput(true);

            out = httpConn.getOutputStream();
            out.write(b);
            out.close();

            // Read the response and write it to standard out.
            isr = new InputStreamReader(httpConn.getInputStream(), "UTF-8");
            in = new BufferedReader(isr);
            while ((responseString = in.readLine()) != null) {
                response = response + responseString;
            }

        } catch (Exception e) {
            e.printStackTrace();

        }
        return response;
}

}
