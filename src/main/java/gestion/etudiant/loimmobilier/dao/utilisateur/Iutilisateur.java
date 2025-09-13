package gestion.etudiant.loimmobilier.dao.utilisateur;

import gestion.etudiant.loimmobilier.model.Utilisateur;

import java.util.List;

public interface Iutilisateur {
    void save(Utilisateur utilisateur);
    void delete(Long id);
    Utilisateur findById(Long id);
    List<Utilisateur> findAll();
    Utilisateur findByEmail(String email); // utile pour login
    List<Utilisateur> findByRole(String role); // ex: récupérer tous les locataires
}
