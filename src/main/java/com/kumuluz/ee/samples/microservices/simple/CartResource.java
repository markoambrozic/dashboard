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
import org.json.JSONArray;
import org.json.JSONObject;

import com.kumuluz.ee.samples.microservices.simple.Models.Cart;
import com.kumuluz.ee.samples.microservices.simple.Models.Item;

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

    @POST
    @Path("/addToCart/{cartId}")
    public Response addItemToCart(Item i, @PathParam("cartId") Integer id) {
        em.getTransaction().begin();

        Cart c = em.find(Cart.class, id);

        String cartJSONString = c.getCartJSON();
        JSONObject cartJSON = new JSONObject(cartJSONString);

        JSONArray items = (JSONArray) cartJSON.get("items");
        items.put(i.getItemJSON());

        cartJSON.put("items", items);
        c.setCartJSON(cartJSON.toString());

        em.persist(c);
        em.getTransaction().commit();

        return Response.status(Response.Status.OK).entity(c).build();
    }

    @POST
    @Path("/removeFromCart/{cartId}")
    public Response removeItemFromCart(Item i, @PathParam("cartId") Integer id) throws Exception {
        em.getTransaction().begin();

        Cart c = em.find(Cart.class, id);

        String cartJSONString = c.getCartJSON();
        JSONObject cartJSON = new JSONObject(cartJSONString);

        JSONArray items = (JSONArray) cartJSON.get("items");

        int index = 0;
        for (Object item : items) {
            JSONObject itemJSON = (JSONObject) item;

            if (Integer.parseInt(itemJSON.get("productId").toString()) == i.getProductId()) {
                break;
            }
            index++;
        }

        items.remove(index);

        cartJSON.put("items", items);
        c.setCartJSON(cartJSON.toString());

        em.persist(c);
        em.getTransaction().commit();

        return Response.status(Response.Status.OK).entity(c).build();
    }

}
