package gestion.etudiant.loimmobilier.model;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
public class ContratLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    private LocalDateTime dateDebut;
    @NonNull
    private LocalDateTime dateFin;
    private String nom;
    private float montant;
    @Enumerated(EnumType.STRING)
    private StatusContrat statusContrat;
    public enum StatusContrat{
       TERMINER,
       ACTIVE,
    }
    // Un contrat concerne un locataire (utilisateur)
    @ManyToOne
    @JoinColumn(name = "locataire_id", nullable = false)
    private Utilisateur locataire;

    // Un contrat est lié à une unité de location
    @ManyToOne
    @JoinColumn(name = "unite_id", nullable = false)
    private UniteLocation uniteLocation;

    // Un contrat peut avoir plusieurs paiements
    @OneToMany(mappedBy = "contratLocation", cascade = CascadeType.ALL)
    private java.util.List<Paiement> paiements;
}
