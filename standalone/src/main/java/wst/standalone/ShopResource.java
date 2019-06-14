package wst.standalone;

import wst.dao.ShopDAO;
import wst.entity.Shop;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

import java.util.List;

@Path("/shops")
public class ShopResource {
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<Shop> read(
            @QueryParam("name") String name,
            @QueryParam("city") String city,
            @QueryParam("isActive") Boolean isActive,
            @QueryParam("type") String type,
            @QueryParam("address") String address) throws Exception {
        ShopDAO shopDAO = new ShopDAO();

        try {
            return shopDAO.read(name, city, address, isActive, type);
        } catch (Exception err) {
            System.out.println(err.getMessage());
            throw err;
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response create(Shop shop, @Context UriInfo uriInfo) {
        ShopDAO shopDAO = new ShopDAO();
        long createdId = shopDAO.create(
            shop.getName(),
            shop.getCity(),
            shop.getAddress(),
            shop.getIsActive(),
            shop.getType());

        UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        builder.path(String.valueOf(createdId));
        return Response.created(builder.build()).entity(String.valueOf(createdId)).build();
    }

    @DELETE
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/{id}")
    public String delete(@PathParam("id") long id) {
        ShopDAO shopDAO = new ShopDAO();
        return String.valueOf(shopDAO.delete(id));
    }

    @PUT
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/{id}")
    public String update(@PathParam("id") long updateId, Shop shop) {
        ShopDAO shopDAO = new ShopDAO();
        return String.valueOf(shopDAO.update(
            updateId,
            shop.getName(),
            shop.getCity(),
            shop.getAddress(),
            shop.getIsActive(),
            shop.getType()));
    }
}