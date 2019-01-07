package com.kumuluz.ee.samples.microservices.simple;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kumuluz.ee.logs.LogManager;
import com.kumuluz.ee.logs.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.kumuluz.ee.logs.cdi.Log;
import javax.inject.Inject;

import com.kumuluz.ee.discovery.annotations.DiscoverService;
import org.json.JSONObject;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;




@Path("/dashboard")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Log
public class DashboardResource {

    @PersistenceContext
    private EntityManager em;

    private static final Logger LOG = LogManager.getLogger(DashboardResource.class.getName());

    //TODO spremeni service v tisti za ustvarjanje in drugo
    //narejen primer klicanja
    @Inject
    @DiscoverService(value = "emailing-service", environment = "dev", version = "*")
    private Optional<WebTarget> target;

    @GET
    public Response getDashboard() {
        JSONObject obj = new JSONObject();

        JSONObject mailobj = new JSONObject();
        obj.put("dashboard", "fancy info about people");

        mailobj.put("from","somemail");
        mailobj.put("to","somemail");
        mailobj.put("subject","somemail");
        mailobj.put("body","somemail");

        if (target.isPresent()) {
            WebTarget service = target.get().path("email/sendEmail");

            Response response;
            try {

                response = service.request().post(Entity.json(mailobj.toString()));
            } catch (ProcessingException e) {
                e.printStackTrace();
                return Response.ok(obj.toString()).build();
            } catch (Exception e) {
                e.printStackTrace();
                return Response.ok(obj.toString()).build();
            }

            return Response.fromResponse(response).build();
        } else {
            return Response.ok(obj.toString()).build();
        }



    }


}
