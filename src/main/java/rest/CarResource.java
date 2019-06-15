package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import exceptions.GenericExceptionMapper;
import facade.Facade;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("car")
public class CarResource {

    private final GenericExceptionMapper GEM = new GenericExceptionMapper();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private final Facade facade = new Facade();

    @Context
    private UriInfo context;

    public CarResource() {
    }

    @GET
    @Path("/availablecars/{week}/{address}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getJson(@PathParam("week") String week, @PathParam("address") String address) {
        try {
            return Response.ok().entity(facade.getAllCarData(week, address).replace("]\n[", ",")).build();
        } catch (Exception e) {
            return GEM.toResponse(e);
        }
    }

}
