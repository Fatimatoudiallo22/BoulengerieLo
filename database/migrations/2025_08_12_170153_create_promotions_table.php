<?php
use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

return new class extends Migration
{
    public function up(): void
    {
        Schema::create('promotions', function (Blueprint $table) {
            $table->id();

            // Titre de la promotion
            $table->string('titre');

            // Description optionnelle
            $table->text('description')->nullable();

            // Type de promotion : pourcentage ou montant fixe
            $table->enum('type', ['pourcentage', 'montant'])->default('pourcentage');

            // Valeur de la réduction (ex: 20% ou 5 euros)
            $table->decimal('valeur', 8, 2);

            // Dates de validité
            $table->timestamp('date_debut')->nullable();
            $table->timestamp('date_fin')->nullable();

            // Peut s'appliquer à un produit spécifique ou un pack
            $table->foreignId('produit_id')->nullable()->constrained('produits')->onDelete('cascade');
            $table->foreignId('pack_id')->nullable()->constrained('packs')->onDelete('cascade');


            $table->timestamps();
        });
    }

    public function down(): void
    {
        Schema::dropIfExists('promotions');
    }
};
