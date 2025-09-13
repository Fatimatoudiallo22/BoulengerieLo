package gestion.etudiant.loimmobilier.servlet;

import gestion.etudiant.loimmobilier.dao.immeuble.ImmeubleDao;
import gestion.etudiant.loimmobilier.model.Immeuble;
import gestion.etudiant.loimmobilier.model.Utilisateur;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/Immeuble")
public class ImmeubleServlet extends HttpServlet {

    private ImmeubleDao immeubleDao = new ImmeubleDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        Utilisateur user = (Utilisateur) req.getSession().getAttribute("user");

        if (action == null) action = "list";

        if ("edit".equals(action) || "add".equals(action)) {
            req.getRequestDispatcher("page/immeuble/formImmeuble.jsp").forward(req, resp);

        } else if ("details".equals(action)) {
            Long id = Long.parseLong(req.getParameter("id"));
            Immeuble immeuble = immeubleDao.findById(id);
            req.setAttribute("immeuble", immeuble);
            req.getRequestDispatcher("page/immeuble/detailsImmeuble.jsp").forward(req, resp);

        } else { // Liste
            List<Immeuble> immeubles;

            if (user.getRole() == Utilisateur.Role.ADMIN) {
                immeubles = immeubleDao.findAll(); // admin → tout
            } else if (user.getRole() == Utilisateur.Role.PROPRIO) {
                immeubles = immeubleDao.findByProprietaire(user.getId()); // proprio → ses immeubles
            } else {
                immeubles = immeubleDao.findDisponibles(); // locataire → seulement disponibles
            }

            req.setAttribute("immeubles", immeubles);
            req.getRequestDispatcher("page/immeuble/listeImmeuble.jsp").forward(req, resp);
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        Utilisateur user = (Utilisateur) req.getSession().getAttribute("user"); // Récupération du proprio connecté

        if ("delete".equals(action)) {
            Long id = Long.parseLong(req.getParameter("id"));
            immeubleDao.delete(id);
            resp.sendRedirect("Immeuble");
            return;
        }

        String idStr = req.getParameter("id");
        String nom = req.getParameter("nom");
        String adresse = req.getParameter("adresse");
        String nbreUniteStr = req.getParameter("nbreUnite");
        String equipement = req.getParameter("equipementdispo");
        String description = req.getParameter("description");
        String image = req.getParameter("image");

        try {
            Immeuble immeuble = new Immeuble();
            if (idStr != null && !idStr.isEmpty()) {
                immeuble.setId(Long.parseLong(idStr));
            }
            immeuble.setNom(nom); // ✅ Ajouter nom
            immeuble.setAdresse(adresse);
            immeuble.setNbreunite(nbreUniteStr != null ? Integer.parseInt(nbreUniteStr) : 0);
            immeuble.setEquipementdispo(equipement);
            immeuble.setDescription(description);
            immeuble.setImage(image);

            // 🔑 Associer le proprio connecté
            if (user.getRole() == Utilisateur.Role.PROPRIO) {
                immeuble.setProprietaire(user);
            }

            immeubleDao.save(immeuble);
            resp.sendRedirect("Immeuble");
        } catch (Exception e) {
            throw new ServletException("Erreur lors de l'enregistrement de l'immeuble", e);
        }
    }


}
