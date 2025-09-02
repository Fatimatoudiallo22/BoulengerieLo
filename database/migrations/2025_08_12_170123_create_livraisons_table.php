<?php
use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

return new class extends Migration
{
    public function up(): void
    {
        Schema::create('livraisons', function (Blueprint $table) {
            $table->id();

            // La livraison est liée à une commande
            $table->foreignId('commande_id')->constrained('commandes')->onDelete('cascade');

            // L'employé responsable de la livraison (peut être nullable si pas encore assigné)
            $table->foreignId('employe_id')->nullable()->constrained('utilisateurs')->onDelete('set null');

            // Statut de la livraison
            $table->enum('statut', ['en_preparation', 'en_livraison', 'livree', 'annulee'])->default('en_preparation');

            // Date prévue de livraison
            $table->timestamp('date_prevue')->nullable();

            // Date réelle de livraison
            $table->timestamp('date_livree')->nullable();

            // Localisation / adresse de livraison (optionnel si différente de celle de la commande)
            $table->string('adresse_livraison')->nullable();

            $table->timestamps();
        });
    }

    public function down(): void
    {
        Schema::dropIfExists('livraisons');
    }
};
