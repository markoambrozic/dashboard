
package com.kumuluz.ee.samples.microservices.simple;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import com.kumuluz.ee.discovery.annotations.RegisterService;

@ApplicationPath("/")
@RegisterService
public class DashboardApplication extends Application{
}
