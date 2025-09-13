package gestion.etudiant.loimmobilier.dao.paiement;

import gestion.etudiant.loimmobilier.model.Paiement;

import java.util.List;

public interface IpaiementDao {
    void save(Paiement paiement);
    void delete(Long id);
    Paiement findById(Long id);
    List<Paiement> findAll();
}
