package gestion.etudiant.loimmobilier.dao.immeuble;

import gestion.etudiant.loimmobilier.JpaUtil.JPAUtil;
import gestion.etudiant.loimmobilier.model.Immeuble;
import jakarta.persistence.EntityManager;

import java.util.List;

public class ImmeubleDao implements Iimmeuble {
    @Override
    public void save(Immeuble immeuble) {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            entityManager.getTransaction().begin();
            if (immeuble.getId() == null) {
                entityManager.persist(immeuble); // INSERT
            } else {
                entityManager.merge(immeuble);   // UPDATE
            }
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new RuntimeException(e);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void delete(Long id) {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            entityManager.getTransaction().begin();
            Immeuble immeuble = entityManager.find(Immeuble.class, id);
            if (immeuble != null) {
                entityManager.remove(immeuble);
            }
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new RuntimeException(e);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Immeuble findById(Long id) {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            return entityManager.find(Immeuble.class, id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public List<Immeuble> findAll() {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            return entityManager.createQuery("from Immeuble", Immeuble.class).getResultList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            entityManager.close();
        }
    }





    // Récupérer les immeubles d’un propriétaire
    public List<Immeuble> findByProprietaire(Long proprioId) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            return em.createQuery("SELECT i FROM Immeuble i WHERE i.proprietaire.id = :pid", Immeuble.class)
                    .setParameter("pid", proprioId)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    // Récupérer uniquement les immeubles disponibles
    public List<Immeuble> findDisponibles() {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            return em.createQuery("SELECT i FROM Immeuble i WHERE i.disponible = true", Immeuble.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

}
