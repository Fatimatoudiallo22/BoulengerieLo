package gestion.etudiant.loimmobilier.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class DemandeLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Locataire qui fait la demande
    @ManyToOne
    @JoinColumn(name = "locataire_id", nullable = false)
    private Utilisateur locataire;

    // L’unité demandée
    @ManyToOne
    @JoinColumn(name = "unite_id", nullable = false)
    private UniteLocation uniteLocation;

    private LocalDateTime dateDemande = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    private Statut statut = Statut.EN_ATTENTE;

    public enum Statut {
        EN_ATTENTE,
        ACCEPTEE,
        REFUSEE
    }
}
