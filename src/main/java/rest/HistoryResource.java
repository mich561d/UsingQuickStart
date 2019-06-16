package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import exceptions.GenericExceptionMapper;
import exceptions.HistoryException;
import facade.HistoryFacade;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import utils.PuSelector;

@Path("history")
public class HistoryResource {

    private final GenericExceptionMapper GEM = new GenericExceptionMapper();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private final HistoryFacade HF = HistoryFacade.getInstance(PuSelector.getEntityManagerFactory("pu"));

    @Context
    private UriInfo context;

    public HistoryResource() {
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("admin")
    public Response getJson() {
        try {
            return Response.ok().entity(GSON.toJson(HF.getHistory())).build();
        } catch (HistoryException ex) {
            return GEM.toResponse(ex);
        }
    }
}
