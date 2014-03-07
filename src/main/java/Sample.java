import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.wso2.carbon.utility.jira.json.Project;
import org.wso2.carbon.utility.jira.server.IssueTrackerAdminService;
import org.wso2.carbon.utility.jira.server.SoapAction;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.Date;
import java.util.List;


/**
 * Created by pubudu on 2/11/14.
 */
public class Sample {

    static final String PROJECT_KEY = "TST";
    static final String ISSUE_TYPE_ID = "1";
    static final String PROJECT_NAME= "Test";
    static final String PROJECT_DESCRIPTION= "This is a test project created on " +new Date();
    static final String PRIORITY_ID = "4";
    static final String USER_NAME = "<username>";
    static final String PASSWORD = "<password>";
    static final String SUMMARY_NAME ="This is a Test Created on "+new Date()+"";

    public static void main(String[] args) throws IOException {
        IssueTrackerAdminService issueTrackerAdminService =null;
        issueTrackerAdminService = new IssueTrackerAdminService();
        String type = issueTrackerAdminService.getIssueTrackerType();
        System.out.println(type);

        System.out.println(issueTrackerAdminService.isIssueTrackerProjectExist(USER_NAME,PASSWORD,"wso2 Carbon","https://wso2.org/jira/browse/CARBON"));
        /*String authToken = null;
        try {
            authToken = new SoapAction().getAuthToken(USER_NAME,PASSWORD);
        } catch (SAXException e) {
            e.printStackTrace();
        }
        System.out.println(authToken);

        //issueTrackerAdminService.createIssueTrackerProject(USER_NAME,PASSWORD,"TST",PROJECT_NAME,PROJECT_DESCRIPTION,"","<lead>") ;
*/
       // String url = "https://wso2.org/jira/rest/api/2/project/";
/*

        String projectURL = "https://wso2.org/jira/browse/CARBON";
        String USER_AGENT = "Mozilla/5.0";
        String ACCEPT_HEADER="application/json";
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(issueTrackerAdminService.endPointCreator(projectURL,"rest"));



        // add request header
        request.addHeader("User-Agent", USER_AGENT);
        request.addHeader("Accept",ACCEPT_HEADER);
        HttpResponse response = client.execute(request);
        ObjectMapper mapper = new ObjectMapper();


        List<Project> myObjects = mapper.readValue(response.getEntity().getContent(), new TypeReference<List<Project>>(){});
        System.out.println(myObjects.get(0).getName());

        System.out.println("Response Code : "
                + response.getStatusLine().getStatusCode());

*/



    }
}
