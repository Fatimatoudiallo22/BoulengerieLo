package gestion.etudiant.loimmobilier.servlet;

import gestion.etudiant.loimmobilier.dao.utilisateur.Iutilisateur;
import gestion.etudiant.loimmobilier.dao.utilisateur.UtilisateurDao;
import gestion.etudiant.loimmobilier.model.Utilisateur;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/utilisateur")
public class UtilisateurServlet extends HttpServlet {

    private Iutilisateur utilisateurDao = new UtilisateurDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) action = "list";

        if ("add".equals(action)) {
            req.getRequestDispatcher("/page/utilisateur/addUtilisateur.jsp").forward(req, resp);

        } else if ("edit".equals(action)) {
            Long editId = Long.parseLong(req.getParameter("id"));
            Utilisateur userToEdit = utilisateurDao.findById(editId);
            req.setAttribute("utilisateur", userToEdit);
            req.getRequestDispatcher("/page/utilisateur/editUtilisateur.jsp").forward(req, resp);

        } else if ("delete".equals(action)) {
            Long deleteId = Long.parseLong(req.getParameter("id"));
            utilisateurDao.delete(deleteId);
            resp.sendRedirect("utilisateur");

        } else { // list
            List<Utilisateur> utilisateurs = utilisateurDao.findAll();
            req.setAttribute("utilisateurs", utilisateurs);
            req.getRequestDispatcher("/page/utilisateur/listUtilisateur.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idStr = req.getParameter("id");
        String nom = req.getParameter("nom");
        String prenom = req.getParameter("prenom");
        String adresse = req.getParameter("adresse");
        String email = req.getParameter("email");
        String telephone = req.getParameter("telephone");
        String password = req.getParameter("password");
        String roleStr = req.getParameter("role");

        Utilisateur.Role role = Utilisateur.Role.valueOf(roleStr.toUpperCase());


        Utilisateur utilisateur;
        if (idStr == null || idStr.isEmpty()) { // nouveau utilisateur
            utilisateur = new Utilisateur();
            utilisateur.setDateInscription(LocalDate.now());
        } else { // modification
            Long id = Long.parseLong(idStr);
            utilisateur = utilisateurDao.findById(id);
        }

        utilisateur.setNom(nom);
        utilisateur.setPrenom(prenom);
        utilisateur.setAdresse(adresse);
        utilisateur.setEmail(email);
        utilisateur.setTelephone(telephone);
        utilisateur.setPassword(password);
        utilisateur.setRole(role);

        utilisateurDao.save(utilisateur);
        resp.sendRedirect("utilisateur");
    }
}
