package gestion.etudiant.loimmobilier.dao.immeuble;

import gestion.etudiant.loimmobilier.model.Immeuble;

import java.util.List;

public interface Iimmeuble {
    void save(Immeuble immeuble);
    void delete(Long id);
    Immeuble findById(Long id);
    List<Immeuble> findAll();
}
