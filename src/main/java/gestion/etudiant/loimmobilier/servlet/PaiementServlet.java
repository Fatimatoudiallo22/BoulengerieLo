package gestion.etudiant.loimmobilier.servlet;

import gestion.etudiant.loimmobilier.dao.contratLocation.ContratLocationDao;
import gestion.etudiant.loimmobilier.dao.paiement.PaiementDao;
import gestion.etudiant.loimmobilier.email.EmailUtil;
import gestion.etudiant.loimmobilier.model.ContratLocation;
import gestion.etudiant.loimmobilier.model.Paiement;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@WebServlet("/paiement")
public class PaiementServlet extends HttpServlet {

    private PaiementDao paiementDao = new PaiementDao();
    private ContratLocationDao contratLocationDao = new ContratLocationDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if ("edit".equals(action) || "add".equals(action)) {
            req.setAttribute("contrats", contratLocationDao.findAll());
            if ("edit".equals(action)) {
                long id = Long.parseLong(req.getParameter("id"));
                Paiement paiement = paiementDao.findById(id);
                req.setAttribute("paiement", paiement);
            }
            req.getRequestDispatcher("page/paiement/formPaiement.jsp").forward(req, resp);

        } else if ("details".equals(action)) {
            long id = Long.parseLong(req.getParameter("id"));
            Paiement paiement = paiementDao.findById(id);
            req.setAttribute("paiement", paiement);
            req.getRequestDispatcher("page/paiement/detailsPaiement.jsp").forward(req, resp);

        } else { // Liste
            List<Paiement> paiements = paiementDao.findAll();
            req.setAttribute("paiements", paiements);
            req.getRequestDispatcher("page/paiement/listePaiement.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if ("delete".equals(action)) {
            long id = Long.parseLong(req.getParameter("id"));
            paiementDao.delete(id);
            resp.sendRedirect("paiement");
            return;
        }

        String idStr = req.getParameter("id");
        String montantStr = req.getParameter("prix");
        String modeStr = req.getParameter("modePaiement");
        String statusStr = req.getParameter("statusPaiement");
        String contratIdStr = req.getParameter("contratId");

        try {
            Paiement paiement = new Paiement();
            if (idStr != null && !idStr.isEmpty()) paiement.setId(Long.parseLong(idStr));
            if (montantStr != null && !montantStr.isEmpty()) paiement.setPrix(Float.parseFloat(montantStr));
            if (modeStr != null && !modeStr.isEmpty()) paiement.setModePaiement(Paiement.ModePaiement.valueOf(modeStr));
            if (statusStr != null && !statusStr.isEmpty())
                paiement.setStatusPaiement(Paiement.StatusPaiement.valueOf(statusStr));
            if (contratIdStr != null && !contratIdStr.isEmpty())
                paiement.setContratLocation(contratLocationDao.findById(Long.parseLong(contratIdStr)));

            paiement.setDatepaiement(LocalDateTime.now());

            paiementDao.save(paiement);

            // Vérifier si le paiement est PAYE
            if (paiement.getStatusPaiement() == Paiement.StatusPaiement.PAYE) {
                String emailLocataire = paiement.getContratLocation().getLocataire().getEmail();
                String sujet = "Confirmation de paiement";
                String contenu = "Bonjour " + paiement.getContratLocation().getLocataire().getNom()
                        + ",\n\nVotre paiement de " + paiement.getPrix() + " FCFA a bien été reçu."
                        + "\n\nMerci pour votre confiance.\nL’équipe de gestion immobilière.";

                boolean mailEnvoye = EmailUtil.sendEmail(emailLocataire, sujet, contenu);

                if (!mailEnvoye) {
                    // Ici tu peux afficher un message ou enregistrer l’échec en base
                    System.err.println("⚠️ L’email de confirmation n’a pas pu être envoyé à " + emailLocataire);
                }
            }

            // Redirection vers la liste des paiements
            resp.sendRedirect("paiement");

        } catch (Exception e) {
            throw new ServletException("Erreur lors de l'enregistrement du paiement", e);
        }
    }
}
