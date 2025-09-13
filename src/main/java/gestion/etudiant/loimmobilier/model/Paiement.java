package gestion.etudiant.loimmobilier.model;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
public class Paiement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    private float prix;
    private LocalDateTime datepaiement;
    @Enumerated(EnumType.STRING)
    private ModePaiement modePaiement;
    public enum ModePaiement {
        ESPECE,
        VIREMENT,
        CHEQUE
    }
    private StatusPaiement statusPaiement;
    public enum StatusPaiement {
        EN_RETARD,
        EN_ATTEND,
        PAYE
    }
    // Chaque paiement appartient à un contrat
    @ManyToOne
    @JoinColumn(name = "contrat_id", nullable = false)
    private ContratLocation contratLocation;
}
