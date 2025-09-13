package gestion.etudiant.loimmobilier.model;

import jakarta.persistence.*;
import lombok.*;

import javax.lang.model.element.Name;
import javax.management.relation.Role;
import java.sql.Date;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name="utilisateur")
public class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String prenom;
    private String adresse;
    @NonNull
    private String email;
    private String telephone;
    @NonNull
    private String password;
    private LocalDate dateInscription;
    @Enumerated(EnumType.STRING)
    private Role role;
    public enum Role {
        ADMIN,
        PROPRIO,
        LOCATAIRE
    }
    // Un utilisateur (locataire) peut avoir plusieurs contrats
    @OneToMany(mappedBy = "locataire", cascade = CascadeType.ALL)
    private java.util.List<ContratLocation> contrats;
}

