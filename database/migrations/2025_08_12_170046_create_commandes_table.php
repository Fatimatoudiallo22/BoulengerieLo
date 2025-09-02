<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

return new class extends Migration
{
    /**
     * Run the migrations.
     */
    public function up(): void
    {
        Schema::create('commandes', function (Blueprint $table) {
    $table->id();
    $table->foreignId('utilisateur_id')->constrained('utilisateurs')->onDelete('cascade');
    $table->decimal('prix_total', 8, 2);
    $table->enum('statut', ['en_attente', 'en_cours', 'livree', 'annulee'])->default('en_attente');
    $table->timestamp('date_commande')->useCurrent();
    $table->timestamp('date_livraison')->nullable();
    $table->string('adresse_livraison')->nullable();
    $table->enum('mode_paiement', ['carte', 'especes', 'a_la_livraison'])->default('carte');
    $table->timestamps();
});
    }

    /**
     * Reverse the migrations.
     */
    public function down(): void
    {
        Schema::dropIfExists('commandes');
    }
};
