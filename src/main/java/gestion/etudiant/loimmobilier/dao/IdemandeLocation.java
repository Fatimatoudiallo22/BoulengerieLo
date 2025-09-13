package gestion.etudiant.loimmobilier.dao;

import gestion.etudiant.loimmobilier.model.DemandeLocation;

import java.util.List;

public interface IdemandeLocation {
    void save(DemandeLocation demande);
    void delete(Long id);
    DemandeLocation findById(Long id);
    List<DemandeLocation> findAll();
    List<DemandeLocation> findByLocataire(Long locataireId);
    List<DemandeLocation> findByStatut(String statut);

    List<Long> findUniteIdsByLocataire(Long locataireId);
}
