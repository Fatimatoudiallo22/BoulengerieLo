package gestion.etudiant.loimmobilier.dao.contratLocation;
import gestion.etudiant.loimmobilier.model.ContratLocation;

import java.util.List;
public interface IcontratLocation {
    void save(ContratLocation contratLocation);
    void delete(Long id);
    ContratLocation findById(Long id);
    List<ContratLocation> findAll();
    List<ContratLocation> findByLocataire(Long locataireId);
}
