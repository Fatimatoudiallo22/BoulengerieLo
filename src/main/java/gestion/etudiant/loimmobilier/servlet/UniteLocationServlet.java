package gestion.etudiant.loimmobilier.servlet;

import gestion.etudiant.loimmobilier.dao.DemandeLocationDao;
import gestion.etudiant.loimmobilier.dao.immeuble.ImmeubleDao;
import gestion.etudiant.loimmobilier.dao.uniterLocation.UniterLocationDao;
import gestion.etudiant.loimmobilier.model.Immeuble;
import gestion.etudiant.loimmobilier.model.UniteLocation;
import gestion.etudiant.loimmobilier.model.Utilisateur;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10,      // 10MB
        maxRequestSize = 1024 * 1024 * 50    // 50MB
)

@WebServlet("/UniteLocation")
public class UniteLocationServlet extends HttpServlet {

    private UniterLocationDao uniteLocationDao = new UniterLocationDao();
    private ImmeubleDao immeubleDao = new ImmeubleDao();
    private DemandeLocationDao demandeDao = new DemandeLocationDao(); // <- ajoute ceci

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        Utilisateur user = (Utilisateur) req.getSession().getAttribute("user"); // récup rôle

        if ("edit".equals(action)) {
            Long id = Long.parseLong(req.getParameter("id"));
            UniteLocation unite = uniteLocationDao.findById(id);
            req.setAttribute("unite", unite);

            if (user.getRole() == Utilisateur.Role.ADMIN) {
                req.setAttribute("immeubles", immeubleDao.findAll());
            } else if (user.getRole() == Utilisateur.Role.PROPRIO) {
                req.setAttribute("immeubles", immeubleDao.findByProprietaire(user.getId()));
            }

            req.getRequestDispatcher("page/unitelocation/formUniteLocation.jsp").forward(req, resp);

        } else if ("add".equals(action)) {
            if (user.getRole() == Utilisateur.Role.ADMIN) {
                req.setAttribute("immeubles", immeubleDao.findAll());
            } else if (user.getRole() == Utilisateur.Role.PROPRIO) {
                req.setAttribute("immeubles", immeubleDao.findByProprietaire(user.getId()));
            }
            req.getRequestDispatcher("page/unitelocation/formUniteLocation.jsp").forward(req, resp);

        } else if ("details".equals(action)) {
            Long id = Long.parseLong(req.getParameter("id"));
            UniteLocation unite = uniteLocationDao.findById(id);
            req.setAttribute("unite", unite);
            req.getRequestDispatcher("page/unitelocation/detailsUniteLocation.jsp").forward(req, resp);

        } else { // Liste
            List<UniteLocation> unites;
            if (user.getRole() == Utilisateur.Role.ADMIN) {
                unites = uniteLocationDao.findAll();
            } else if (user.getRole() == Utilisateur.Role.PROPRIO) {
                unites = uniteLocationDao.findByProprietaire(user.getId());
            } else { // LOCATAIRE
                unites = uniteLocationDao.findDisponibles();

                // 🔹 Récupérer les IDs des unités déjà demandées par ce locataire
                List<Long> demandesLocataire = demandeDao.findUniteIdsByLocataire(user.getId());
                req.setAttribute("demandesLocataire", demandesLocataire);
            }

            req.setAttribute("unites", unites);
            req.getRequestDispatcher("page/unitelocation/listeUniteLocation.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        Utilisateur user = (Utilisateur) req.getSession().getAttribute("user"); // proprio ou admin connecté

        if ("delete".equals(action)) {
            Long id = Long.parseLong(req.getParameter("id"));
            uniteLocationDao.delete(id);
            resp.sendRedirect("UniteLocation");
            return;
        }

        String idStr = req.getParameter("id");
        String nom = req.getParameter("nom");
        String numero = req.getParameter("numero");
        String nombrePieceStr = req.getParameter("nombrePiece");
        String surfaceStr = req.getParameter("surface");
        String loyerMensuelStr = req.getParameter("loyerMensuel");
        String dispoStr = req.getParameter("dispo");
        String immeubleIdStr = req.getParameter("immeubleId");
        String imagepathStr = req.getParameter("imagepath");
        try {
            UniteLocation unite = new UniteLocation();

            // Gestion de l'ID pour update
            if (idStr != null && !idStr.isEmpty()) {
                unite.setId(Long.parseLong(idStr));
            }

            // Champs simples
            unite.setNom(nom != null ? nom.trim() : "");
            unite.setNumero(numero != null ? numero.trim() : "");
            unite.setNombrePiece(nombrePieceStr != null && !nombrePieceStr.isEmpty()
                    ? Integer.parseInt(nombrePieceStr) : 0);
            unite.setSurface(surfaceStr != null && !surfaceStr.isEmpty()
                    ? Float.parseFloat(surfaceStr) : 0);
            unite.setLoyerMensuel(loyerMensuelStr != null && !loyerMensuelStr.isEmpty()
                    ? Integer.parseInt(loyerMensuelStr) : 0);
            unite.setImagePath(imagepathStr);
            // Enum dispo avec valeur par défaut LIBRE
            if (dispoStr != null && !dispoStr.isEmpty()) {
                try {
                    unite.setDispo(UniteLocation.Dispo.valueOf(dispoStr));
                } catch (IllegalArgumentException e) {
                    unite.setDispo(UniteLocation.Dispo.LIBRE);
                }
            } else {
                unite.setDispo(UniteLocation.Dispo.LIBRE);
            }

            // Association avec immeuble
            if (immeubleIdStr != null && !immeubleIdStr.isEmpty()) {
                Immeuble immeuble = immeubleDao.findById(Long.parseLong(immeubleIdStr));
                if (immeuble != null) {
                    unite.setImmeuble(immeuble);
                }
            }

            // 🔑 Si PROPRIO, on peut s’assurer qu’il n’ajoute/modifie que ses immeubles
            if (user.getRole() == Utilisateur.Role.PROPRIO && unite.getImmeuble() != null) {
                if (!unite.getImmeuble().getProprietaire().getId().equals(user.getId())) {
                    throw new ServletException("Vous ne pouvez pas modifier une unité d'un immeuble qui ne vous appartient pas.");
                }
            }

            // Sauvegarde
            uniteLocationDao.save(unite);
            resp.sendRedirect("UniteLocation");
        } catch (Exception e) {
            throw new ServletException("Erreur lors de l'enregistrement de l'unité", e);
        }
    }

}
