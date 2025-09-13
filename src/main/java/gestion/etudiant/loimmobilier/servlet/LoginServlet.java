

package gestion.etudiant.loimmobilier.servlet;

import gestion.etudiant.loimmobilier.dao.utilisateur.Iutilisateur;
import gestion.etudiant.loimmobilier.dao.utilisateur.UtilisateurDao;
import gestion.etudiant.loimmobilier.model.Utilisateur;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private Iutilisateur utilisateurDao = new UtilisateurDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/page/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        Utilisateur utilisateur = utilisateurDao.findByEmail(email);

        if (utilisateur != null && utilisateur.getPassword().equals(password)) {
            // Créer une session
            HttpSession session = req.getSession();
            session.setAttribute("user", utilisateur);

            // Redirection selon le rôle
            switch (utilisateur.getRole()) {
                case ADMIN -> resp.sendRedirect("page/adminHome.jsp");
                case PROPRIO -> resp.sendRedirect("page/proprioHome.jsp");
                case LOCATAIRE -> resp.sendRedirect("page/locataireHome.jsp");
                default -> resp.sendRedirect("login");
            }
        } else {
            req.setAttribute("error", "Email ou mot de passe incorrect !");
            req.getRequestDispatcher("/page/login.jsp").forward(req, resp);
        }
    }
}