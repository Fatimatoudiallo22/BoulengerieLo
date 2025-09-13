package gestion.etudiant.loimmobilier.servlet;

import gestion.etudiant.loimmobilier.dao.DemandeLocationDao;
import gestion.etudiant.loimmobilier.dao.IdemandeLocation;
import gestion.etudiant.loimmobilier.model.DemandeLocation;
import gestion.etudiant.loimmobilier.model.UniteLocation;
import gestion.etudiant.loimmobilier.model.Utilisateur;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@WebServlet("/demandeLocation")
public class DemandeLocationServlet extends HttpServlet {

    private IdemandeLocation demandeDao = new DemandeLocationDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) action = "list";

        if ("add".equals(action)) {
            // Page pour ajouter une demande
            req.getRequestDispatcher("/page/demande/addDemande.jsp").forward(req, resp);

        } else if ("edit".equals(action)) {
            // Page pour modifier une demande
            Long editId = Long.parseLong(req.getParameter("id"));
            DemandeLocation demande = demandeDao.findById(editId);
            req.setAttribute("demande", demande);
            req.getRequestDispatcher("/page/demande/editDemande.jsp").forward(req, resp);

        } else if ("delete".equals(action)) {
            // Supprimer une demande
            Long deleteId = Long.parseLong(req.getParameter("id"));
            demandeDao.delete(deleteId);

            // Redirection vers la liste des unités pour tous
            resp.sendRedirect(req.getContextPath() + "/UniteLocation");

        } else { // list
            // Afficher toutes les demandes (admin/proprio)
            List<DemandeLocation> demandes = demandeDao.findAll();
            req.setAttribute("demandes", demandes);

            // Redirection vers liste des unités pour locataire/admin
            req.getRequestDispatcher("page/unitelocation/listeUniteLocation.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idStr = req.getParameter("id");
        String locataireIdStr = req.getParameter("locataireId");
        String uniteIdStr = req.getParameter("uniteId");
        String statutStr = req.getParameter("statut");

        DemandeLocation demande;

        if (idStr == null || idStr.isEmpty()) { // Nouvelle demande
            demande = new DemandeLocation();
            demande.setDateDemande(LocalDateTime.now());
        } else { // Modification
            Long id = Long.parseLong(idStr);
            demande = demandeDao.findById(id);
        }

        // Association du locataire
        if (locataireIdStr != null && !locataireIdStr.isEmpty()) {
            Utilisateur locataire = new Utilisateur();
            locataire.setId(Long.parseLong(locataireIdStr));
            demande.setLocataire(locataire);
        }

        // Association de l'unité
        if (uniteIdStr != null && !uniteIdStr.isEmpty()) {
            UniteLocation unite = new UniteLocation();
            unite.setId(Long.parseLong(uniteIdStr));
            demande.setUniteLocation(unite);
        }

        // Statut
        if (statutStr != null && !statutStr.isEmpty()) {
            demande.setStatut(DemandeLocation.Statut.valueOf(statutStr.toUpperCase()));
        }

        // Sauvegarde de la demande
        demandeDao.save(demande);

        // 🔹 Redirection vers la liste des unités pour tous
        resp.sendRedirect(req.getContextPath() + "/UniteLocation");
    }
}
