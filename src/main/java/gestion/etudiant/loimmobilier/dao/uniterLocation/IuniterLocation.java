package gestion.etudiant.loimmobilier.dao.uniterLocation;

import gestion.etudiant.loimmobilier.model.UniteLocation;

import java.util.List;

public interface IuniterLocation {
    void save(UniteLocation unite);
    void delete(Long id);
    UniteLocation findById(Long id);
    List<UniteLocation> findAll();
}
