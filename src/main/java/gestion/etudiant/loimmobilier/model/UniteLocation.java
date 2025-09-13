package gestion.etudiant.loimmobilier.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
public class UniteLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String numero;
    private int nombrePiece;
    @NonNull
    private float surface;
    @NonNull
    private int loyerMensuel;
    @Enumerated(EnumType.STRING)
    private Dispo dispo;

    private String imagePath;


    // Définition de l'énumération
    public enum Dispo {
        LIBRE,
        OCCUPE
    }
    // Chaque unité appartient à un immeuble
    @ManyToOne
    @JoinColumn(name = "immeuble_id", nullable = false)
    private Immeuble immeuble;

    // Une unité peut avoir plusieurs contrats de location
    @OneToMany(mappedBy = "uniteLocation", cascade = CascadeType.ALL)
    private java.util.List<ContratLocation> contrats;


}
