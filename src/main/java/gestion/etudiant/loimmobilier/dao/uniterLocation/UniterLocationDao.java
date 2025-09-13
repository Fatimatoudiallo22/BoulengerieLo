package gestion.etudiant.loimmobilier.dao.uniterLocation;

import gestion.etudiant.loimmobilier.JpaUtil.JPAUtil;
import gestion.etudiant.loimmobilier.model.UniteLocation;
import jakarta.persistence.EntityManager;

import java.util.List;

public class UniterLocationDao implements IuniterLocation {
    @Override
    public void save(UniteLocation unite) {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            entityManager.getTransaction().begin();
            if (unite.getId() == null) {
                entityManager.persist(unite); // INSERT
            } else {
                entityManager.merge(unite);   // UPDATE
            }
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new RuntimeException("Erreur lors de l’enregistrement de l’unité", e);
        } finally {
            entityManager.close();
        }

    }

    @Override
    public void delete(Long id) {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            entityManager.getTransaction().begin();
            UniteLocation unite = entityManager.find(UniteLocation.class, id);
            if (unite != null) {
                entityManager.remove(unite);
            }
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new RuntimeException("Erreur lors de la suppression de l’unité", e);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public UniteLocation findById(Long id) {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            return entityManager.find(UniteLocation.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la recherche de l’unité par ID", e);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public List<UniteLocation> findAll() {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            return entityManager.createQuery("from UniteLocation", UniteLocation.class).getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la récupération de toutes les unités", e);
        } finally {
            entityManager.close();
        }
    }
    // Récupérer les unités d’un proprio
    public List<UniteLocation> findByProprietaire(Long proprioId) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            return em.createQuery("SELECT u FROM UniteLocation u WHERE u.immeuble.proprietaire.id = :pid", UniteLocation.class)
                    .setParameter("pid", proprioId)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    // Récupérer seulement les unités libres
    public List<UniteLocation> findDisponibles() {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            return em.createQuery("SELECT u FROM UniteLocation u WHERE u.dispo = :d", UniteLocation.class)
                    .setParameter("d", UniteLocation.Dispo.LIBRE)
                    .getResultList();
        } finally {
            em.close();
        }
    }

}
