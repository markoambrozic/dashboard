package com.kumuluz.ee.samples.microservices.simple;

import com.kumuluz.ee.logs.LogManager;
import com.kumuluz.ee.logs.Logger;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import com.kumuluz.ee.logs.cdi.Log;
import com.kumuluz.ee.logs.cdi.LogParams;

import com.kumuluz.ee.samples.microservices.simple.Models.Cart;

@Path("/cart")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Log
public class CartResource {

    @PersistenceContext
    private EntityManager em;

    private static final Logger LOG = LogManager.getLogger(CartResource.class.getName());

    @GET
    public Response getCarts() {
        TypedQuery<Cart> query = em.createNamedQuery("Cart.findAll", Cart.class);

        List<Cart> carts = query.getResultList();

        return Response.ok(carts).build();
    }

    @GET
    @Path("/{id}")
    public Response getCart(@PathParam("id") Integer id) {
        Cart c = em.find(Cart.class, id);

        if (c == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        return Response.ok(c).build();
    }

    @POST
    public Response createNewCart() {
        Cart c = new Cart();
        c.setId(null);
        c.setCartJSON("{[]}");

        em.getTransaction().begin();

        em.persist(c);

        em.getTransaction().commit();

        return Response.status(Response.Status.CREATED).entity(c.getId()).build();
    }

}
