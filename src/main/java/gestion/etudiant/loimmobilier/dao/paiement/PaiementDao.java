package gestion.etudiant.loimmobilier.dao.paiement;

import gestion.etudiant.loimmobilier.JpaUtil.JPAUtil;
import gestion.etudiant.loimmobilier.dao.immeuble.Iimmeuble;
import gestion.etudiant.loimmobilier.model.Paiement;
import jakarta.persistence.EntityManager;

import java.util.List;

public class PaiementDao implements IpaiementDao {
    @Override
    public void save(Paiement paiement) {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            entityManager.getTransaction().begin();
            if (paiement.getId() == null) {
                entityManager.persist(paiement);
            } else {
                entityManager.merge(paiement);
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
            Paiement paiement = entityManager.find(Paiement.class, id);
            if (paiement != null) {
                entityManager.remove(paiement);
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
    public Paiement findById(Long id) {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            return entityManager.find(Paiement.class, id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public List<Paiement> findAll() {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            return entityManager.createQuery("from Paiement", Paiement.class).getResultList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            entityManager.close();
        }
    }
}
