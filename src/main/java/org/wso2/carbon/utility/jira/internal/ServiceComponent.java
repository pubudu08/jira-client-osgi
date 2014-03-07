package org.wso2.carbon.utility.jira.internal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.ComponentContext;
import org.wso2.carbon.utility.issuetracker.IIssueTracker;
import org.wso2.carbon.utility.jira.server.IssueTrackerAdminService;

/**
 * Created by pubudu on 2/12/14.
 * @scr.component name="org.wso2.carbon.utility.jira"
 * immediate="true"
 */
public class ServiceComponent {
    private ServiceRegistration registration;

    private static IssueTrackerAdminService adminService;
    private static BundleContext bundleContext;
    private static final Log logger = LogFactory.getLog(ServiceComponent.class);

    protected void activate(ComponentContext context) {
        logger.info("Issue Tracker Service: Jira bundle is activated");
        adminService = new IssueTrackerAdminService();
        bundleContext = context.getBundleContext();
        registration = bundleContext.registerService(IIssueTracker.class.getName(),adminService, null);
    }

    protected void deactivate(ComponentContext context) {
        logger.info("Issue Tracker Service: Jira bundle is deactivated");
        registration.unregister();
        adminService = null;
        bundleContext = null;
    }

}
