package gestion.etudiant.loimmobilier.model;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Entity

public class Immeuble {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    private String nom;
    private String adresse;
    @NonNull
    private int nbreunite;
    private String equipementdispo;
    private String description;
    @Column(name = "image")
    private String image;
    @OneToMany(mappedBy = "immeuble",cascade = CascadeType.ALL)
    private java.util.List<UniteLocation> unites;
    private boolean disponible = true; // par défaut disponible
    // ✅ Ajout relation avec propriétaire
    @ManyToOne
    @JoinColumn(name = "proprietaire_id")
    private Utilisateur proprietaire;
}