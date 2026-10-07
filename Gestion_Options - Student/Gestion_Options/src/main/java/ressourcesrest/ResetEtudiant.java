package ressourcesrest;

import entities.Etudiant;
import entities.EtudiantList;
import entities.Option;
import metiers.EtudiantBusiness;
import metiers.OptionBusiness;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("etudiants")
public class ResetEtudiant {
    private EtudiantBusiness etudB = new EtudiantBusiness();
    private OptionBusiness optB = new OptionBusiness();

    // B.1 : création (404 si l'option n'existe pas)
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addEtudiant(Etudiant etudiant) {
        if (etudiant.getOption() != null && etudB.addEtudiant(etudiant)) {
            return Response.status(200).build();
        }
        return Response.status(404).build();
    }

    // B.2 : liste de tous les étudiants
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllEtudiants() {
        return Response.status(200).entity(etudB.getAllEtudiants()).build();
    }

    // B.6 : étudiants d'une option, en XML
    // (chemin littéral "option", prioritaire sur "{id}")
    @GET
    @Path("option")
    @Produces(MediaType.APPLICATION_XML)
    public Response getEtudiantsByOption(@QueryParam("codeOption") int codeOption) {
        Option option = optB.getOptionByCode(codeOption);
        if (option == null) {
            return Response.status(404).build();
        }
        List<Etudiant> liste = etudB.getEtudiantsByOption(option);
        return Response.status(200).entity(new EtudiantList(liste)).build();
    }

    // B.3 : récupération par identifiant
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEtudiant(@PathParam("id") String id) {
        Etudiant e = etudB.getEtudiantByIdentifiant(id);
        if (e != null) {
            return Response.status(200).entity(e).build();
        }
        return Response.status(404).build();
    }

    // B.4 : suppression
    @DELETE
    @Path("{id}")
    public Response deleteEtudiant(@PathParam("id") String id) {
        if (etudB.deleteEtudiant(id)) {
            return Response.noContent().build();   // 204
        }
        return Response.status(404).build();
    }

    // B.5 : modification
    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateEtudiant(@PathParam("id") String id, Etudiant etudiant) {
        if (etudB.updateEtudiant(id, etudiant)) {
            return Response.status(200).build();
        }
        return Response.status(404).build();
    }

}
