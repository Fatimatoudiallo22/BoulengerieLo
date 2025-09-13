package gestion.etudiant.loimmobilier.dao.utilisateur;


import gestion.etudiant.loimmobilier.JpaUtil.JPAUtil;
import gestion.etudiant.loimmobilier.model.Utilisateur;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class UtilisateurDao implements Iutilisateur {
    @Override
    public void save(Utilisateur utilisateur) {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            entityManager.getTransaction().begin();
            if (utilisateur.getId() == null) {
                entityManager.persist(utilisateur); // INSERT
            } else {
                entityManager.merge(utilisateur);   // UPDATE
            }
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new RuntimeException("Erreur lors de l'enregistrement de l'utilisateur", e);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void delete(Long id) {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            entityManager.getTransaction().begin();
            Utilisateur utilisateur = entityManager.find(Utilisateur.class, id);
            if (utilisateur != null) {
                entityManager.remove(utilisateur);
            }
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new RuntimeException("Erreur lors de la suppression de l'utilisateur", e);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Utilisateur findById(Long id) {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            return entityManager.find(Utilisateur.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la recherche de l'utilisateur par ID", e);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public List<Utilisateur> findAll() {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            return entityManager.createQuery("from Utilisateur", Utilisateur.class).getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la récupération de tous les utilisateurs", e);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Utilisateur findByEmail(String email) {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            TypedQuery<Utilisateur> query = entityManager.createQuery(
                    "SELECT u FROM Utilisateur u WHERE u.email = :email", Utilisateur.class);
            query.setParameter("email", email);
            return query.getResultStream().findFirst().orElse(null);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la recherche de l'utilisateur par email", e);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public List<Utilisateur> findByRole(String role) {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            TypedQuery<Utilisateur> query = entityManager.createQuery(
                    "SELECT u FROM Utilisateur u WHERE u.role = :role", Utilisateur.class);
            query.setParameter("role", role);
            return query.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la recherche des utilisateurs par rôle", e);
        } finally {
            entityManager.close();
        }
    }
}
