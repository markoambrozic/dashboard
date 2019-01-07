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

    @Inject
    @DiscoverService(value = "emailing-service", environment = "dev", version = "*")
    private Optional<WebTarget> target;

    //TODO ustvari
    @GET
    public Response getDashboard() {
        JSONObject obj = new JSONObject();

        obj.put("dashboard", "fancy info about people");

        return Response.ok(obj.toString()).build();
    }


}
