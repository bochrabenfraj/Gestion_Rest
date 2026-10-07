package ressourcesrest;

import entities.Option;
import metiers.OptionBusiness;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

import static javax.ws.rs.core.Response.status;

@Path("options")
public class RestOption {

        public OptionBusiness optB= new OptionBusiness();
        @GET
        @Produces(MediaType.APPLICATION_JSON)
        public Response getAllOptions(@QueryParam("domaine") String domaine){
            List<Option> liste;
            if (domaine == null) {
                liste = optB.getListeOptions();
            } else {
                liste = optB.getOptionsByDomaine(domaine);
            }
            return Response.status(200).entity(liste).build();
        }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addOption(Option option) {

        if (option != null && optB.addOption(option)) {
            return Response.status(Response.Status.OK).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @DELETE
    @Path("{code}")
    public Response deleteOption(@PathParam("code") int code) {
        if (optB.deleteOption(code)) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @PUT
    @Path("{code}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateOption(@PathParam("code") int code, Option option) {
        if (optB.updateOption(code, option)) {
            return Response.status(200).build();
        }
        return Response.status(404).build();
    }

    @GET
    @Path("{code}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOptionByCode(@PathParam("code") int code) {
        Option option = optB.getOptionByCode(code);
        if (option == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.status(Response.Status.OK).entity(option).build();
    }
    }











