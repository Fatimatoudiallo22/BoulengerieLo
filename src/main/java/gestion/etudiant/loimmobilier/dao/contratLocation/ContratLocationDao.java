package gestion.etudiant.loimmobilier.dao.contratLocation;

import gestion.etudiant.loimmobilier.JpaUtil.JPAUtil;
import gestion.etudiant.loimmobilier.model.ContratLocation;
import jakarta.persistence.EntityManager;

import java.util.List;

public class ContratLocationDao implements IcontratLocation {

    @Override
    public void save(ContratLocation contratLocation) {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            entityManager.getTransaction().begin();
            if (contratLocation.getId() == null) {
                entityManager.persist(contratLocation); // INSERT
            } else {
                entityManager.merge(contratLocation);   // UPDATE
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
            ContratLocation contratLocation = entityManager.find(ContratLocation.class, id);
            if (contratLocation != null) {  // ✅ correction : on vérifie si l'objet existe
                entityManager.remove(contratLocation);
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
    public ContratLocation findById(Long id) {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            return entityManager.find(ContratLocation.class, id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public List<ContratLocation> findAll() {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            return entityManager.createQuery("from ContratLocation", ContratLocation.class).getResultList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public List<ContratLocation> findByLocataire(Long locataireId) {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            return entityManager.createQuery(
                            "SELECT c FROM ContratLocation c WHERE c.locataire.id = :locataireId", ContratLocation.class)
                    .setParameter("locataireId", locataireId)
                    .getResultList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            entityManager.close();
        }
    }
}
