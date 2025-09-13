package gestion.etudiant.loimmobilier.dao;

import gestion.etudiant.loimmobilier.JpaUtil.JPAUtil;
import gestion.etudiant.loimmobilier.model.DemandeLocation;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class DemandeLocationDao  implements IdemandeLocation {
    @Override
    public void save(DemandeLocation demande) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            if (demande.getId() == null) {
                em.persist(demande);  // INSERT
            } else {
                em.merge(demande);    // UPDATE
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Erreur lors de l'enregistrement de la demande", e);
        } finally {
            em.close();
        }
    }

    @Override
    public void delete(Long id) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            DemandeLocation demande = em.find(DemandeLocation.class, id);
            if (demande != null) {
                em.remove(demande);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Erreur lors de la suppression de la demande", e);
        } finally {
            em.close();
        }
    }

    @Override
    public DemandeLocation findById(Long id) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            return em.find(DemandeLocation.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la recherche de la demande par ID", e);
        } finally {
            em.close();
        }
    }

    @Override
    public List<DemandeLocation> findAll() {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            return em.createQuery("from DemandeLocation", DemandeLocation.class).getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la récupération de toutes les demandes", e);
        } finally {
            em.close();
        }
    }

    @Override
    public List<DemandeLocation> findByLocataire(Long locataireId) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            TypedQuery<DemandeLocation> query = em.createQuery(
                    "SELECT d FROM DemandeLocation d WHERE d.locataire.id = :locataireId", DemandeLocation.class);
            query.setParameter("locataireId", locataireId);
            return query.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la recherche des demandes par locataire", e);
        } finally {
            em.close();
        }
    }

    @Override
    public List<DemandeLocation> findByStatut(String statut) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            TypedQuery<DemandeLocation> query = em.createQuery(
                    "SELECT d FROM DemandeLocation d WHERE d.statut = :statut", DemandeLocation.class);
            query.setParameter("statut", DemandeLocation.Statut.valueOf(statut));
            return query.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la recherche des demandes par statut", e);
        } finally {
            em.close();
        }
    }
    @Override
    public List<Long> findUniteIdsByLocataire(Long locataireId) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            TypedQuery<Long> query = em.createQuery(
                    "SELECT d.uniteLocation.id FROM DemandeLocation d WHERE d.locataire.id = :locataireId",
                    Long.class
            );
            query.setParameter("locataireId", locataireId);
            return query.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la récupération des unités déjà demandées", e);
        } finally {
            em.close();
        }
    }
}