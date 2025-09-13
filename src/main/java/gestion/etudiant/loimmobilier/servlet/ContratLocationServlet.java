package gestion.etudiant.loimmobilier.servlet;

import gestion.etudiant.loimmobilier.dao.contratLocation.ContratLocationDao;
import gestion.etudiant.loimmobilier.dao.uniterLocation.UniterLocationDao;
import gestion.etudiant.loimmobilier.dao.utilisateur.UtilisateurDao;
import gestion.etudiant.loimmobilier.model.ContratLocation;
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

@WebServlet("/ContratLocation")
public class ContratLocationServlet extends HttpServlet {

    private ContratLocationDao contratLocationDao = new ContratLocationDao();
    private UniterLocationDao uniteDao = new UniterLocationDao();
    private UtilisateurDao locataireDao = new UtilisateurDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        Utilisateur user = (Utilisateur) req.getSession().getAttribute("user"); // 🔑 utilisateur connecté

        if ("edit".equals(action)) {
            // ⚠️ Bloquer si locataire
            if (user.getRole() == Utilisateur.Role.LOCATAIRE) {
                resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Accès refusé");
                return;
            }
            long id = Long.parseLong(req.getParameter("id"));
            ContratLocation contrat = contratLocationDao.findById(id);
            req.setAttribute("contrat", contrat);
            req.setAttribute("locataires", locataireDao.findAll());
            req.setAttribute("unites", uniteDao.findAll());
            req.getRequestDispatcher("page/contralocation/formContratLocation.jsp").forward(req, resp);

        } else if ("add".equals(action)) {
            if (user.getRole() == Utilisateur.Role.LOCATAIRE) {
                resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Accès refusé");
                return;
            }
            req.setAttribute("locataires", locataireDao.findAll());
            req.setAttribute("unites", uniteDao.findAll());
            req.getRequestDispatcher("page/contralocation/formContratLocation.jsp").forward(req, resp);

        } else if ("details".equals(action)) {
            long id = Long.parseLong(req.getParameter("id"));
            ContratLocation contrat = contratLocationDao.findById(id);

            // 🔒 locataire ne peut voir que son contrat
            if (user.getRole() == Utilisateur.Role.LOCATAIRE &&
                    !contrat.getLocataire().getId().equals(user.getId())) {
                resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Accès refusé");
                return;
            }

            req.setAttribute("contrat", contrat);
            req.getRequestDispatcher("page/contralocation/detailsContratLocation.jsp").forward(req, resp);

        } else { // Liste
            List<ContratLocation> contrats;
            if (user.getRole() == Utilisateur.Role.LOCATAIRE) {
                contrats = contratLocationDao.findByLocataire(user.getId());
            } else {
                contrats = contratLocationDao.findAll();
            }
            req.setAttribute("contrats", contrats);
            req.setAttribute("role", user.getRole()); // utile dans la JSP
            req.getRequestDispatcher("page/contralocation/listeContratLocation.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        Utilisateur user = (Utilisateur) req.getSession().getAttribute("user"); // 🔑 utilisateur connecté

        if ("delete".equals(action)) {
            if (user.getRole() == Utilisateur.Role.LOCATAIRE) {
                resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Suppression interdite");
                return;
            }
            long id = Long.parseLong(req.getParameter("id"));
            contratLocationDao.delete(id);
            resp.sendRedirect("ContratLocation");
            return;
        }

        if (user.getRole() == Utilisateur.Role.LOCATAIRE) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Modification interdite");
            return;
        }

        String idStr = req.getParameter("id");
        String nomStr = req.getParameter("nom");
        String montantStr = req.getParameter("montant");
        String dateDebutStr = req.getParameter("dateDebut");
        String dateFinStr = req.getParameter("dateFin");
        String locataireIdStr = req.getParameter("locataireId");
        String uniteIdStr = req.getParameter("uniteId");

        try {
            ContratLocation contrat = new ContratLocation();
            if (idStr != null && !idStr.isEmpty()) contrat.setId(Long.parseLong(idStr));
            if (nomStr != null && !nomStr.isEmpty()) contrat.setNom(nomStr);
            if (montantStr != null && !montantStr.isEmpty()) contrat.setMontant(Float.parseFloat(montantStr));
            if (dateDebutStr != null && !dateDebutStr.isEmpty()) contrat.setDateDebut(LocalDateTime.parse(dateDebutStr));
            if (dateFinStr != null && !dateFinStr.isEmpty()) contrat.setDateFin(LocalDateTime.parse(dateFinStr));
            if (locataireIdStr != null && !locataireIdStr.isEmpty()) contrat.setLocataire(locataireDao.findById(Long.parseLong(locataireIdStr)));
            if (uniteIdStr != null && !uniteIdStr.isEmpty()) contrat.setUniteLocation(uniteDao.findById(Long.parseLong(uniteIdStr)));

            contratLocationDao.save(contrat);
            resp.sendRedirect("ContratLocation");
        } catch (Exception e) {
            throw new ServletException("Erreur lors de l'enregistrement du contrat", e);
        }
    }
}
