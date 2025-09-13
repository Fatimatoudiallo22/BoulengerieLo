package gestion.etudiant.loimmobilier.servlet;

import gestion.etudiant.loimmobilier.model.Utilisateur;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter("/*")
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        HttpSession session = req.getSession(false);
        String uri = req.getRequestURI();

        // Pages publiques
        if (uri.endsWith("login") || uri.contains("css") || uri.contains("js")) {
            chain.doFilter(request, response);
            return;
        }

        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        Utilisateur user = (Utilisateur) session.getAttribute("user");

        // Restreindre l'accès selon le rôle
        if (uri.contains("/admin/") && user.getRole() != Utilisateur.Role.ADMIN) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        if (uri.contains("/proprio/") && user.getRole() != Utilisateur.Role.PROPRIO) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        if (uri.contains("/locataire/") && user.getRole() != Utilisateur.Role.LOCATAIRE) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        chain.doFilter(request, response);
    }
}


