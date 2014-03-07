package org.wso2.carbon.utility.jira.server;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.wso2.carbon.utility.issuetracker.IIssueTracker;
import org.wso2.carbon.utility.jira.json.Project;
import org.wso2.carbon.utility.jira.util.XMLModifier;

import java.io.IOException;
import java.util.List;

/**
 * This IssueTrackerAdminService class represents a comprehensive implementation of the Jira SOAP & REST API
 * @author Pubudu Dissanayake : pubudud@wso2.com on 2/12/14.
 */
public class IssueTrackerAdminService implements IIssueTracker {
    SoapAction soapAction = null;
    String soapURL= null;
    String restURl = null;

    /**
     * This method will returns artifact type
     * @return <code>jira</code>
     */
    @Override
    public String getIssueTrackerType() {
        return "jira";
    }

    /**
     * This method will create a project which is related to iss
     * @param username valid username to access api
     * @param password  valid password to access api
     * @param projectKey  shorten name of the project
     * @param name     name of the project
     * @param description   description fo the project
     * @param url   optional field : provide url for the project
     * @param lead   required field : lead of the project
     * @return  <code>true</code> if project get successfully created
     */
    @Override
    public boolean createIssueTrackerProject(String username, String password,String projectKey,String name,String description,String url,String lead) {
        String respond;
        soapAction = new SoapAction();
        soapURL = endPointCreator(url,"soap");
        respond= soapAction.createProject(username,password,soapURL,projectKey,name,description,url,lead);
        //TODO boolean check
        respond =XMLModifier.modifyXML(respond);

        System.out.println(respond);

        return true;
    }

    /**
     * check whether project is exists or not
     * @param username  valid username to access api
     * @param password  valid password to access api
     * @param projectKey shorten name of the project
     * @param url  endpoint url for the api
     * @return  <code>true</code> if project already exists
     */
    @Override
    public boolean isIssueTrackerProjectExist(String username, String password,String projectName,String url) {


        String USER_AGENT = "Mozilla/5.0";
        String ACCEPT_HEADER="application/json";
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(endPointCreator(url,"rest"));

        request.addHeader("User-Agent", USER_AGENT);
        request.addHeader("Accept",ACCEPT_HEADER);
        try {
            HttpResponse response = client.execute(request);

        ObjectMapper mapper = new ObjectMapper();
        List<Project> myObjects = mapper.readValue(response.getEntity().getContent(), new TypeReference<List<Project>>(){});
         for(Project project:myObjects ){
              if(project.getName().toLowerCase().equals(projectName.toLowerCase())){
                  return true;
              }
         }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }


    public String endPointCreator(String endpointURl,String serviceType){
        String[] items;
        if(serviceType.equals("soap")){

            items = endpointURl.split("/browse/");
            endpointURl = items[0]+"/rpc/soap/jirasoapservice-v2";

        }else if(serviceType.equals("rest")){
            items = endpointURl.split("/browse/");
            endpointURl = items[0]+"/rest/api/2/project/";
        }
        System.out.println(endpointURl);
        return endpointURl;

    }
}
