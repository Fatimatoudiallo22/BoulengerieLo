<?php
use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

return new class extends Migration
{
    public function up(): void
    {
        Schema::create('packs', function (Blueprint $table) {
            $table->id();
            $table->string('nom');
            $table->text('description')->nullable();
            $table->decimal('prix', 8, 2);
            $table->timestamps();
        });

        // Table pivot pack_produit (produits dans un pack)
        Schema::create('pack_produit', function (Blueprint $table) {
            $table->id();
            $table->foreignId('pack_id');
            $table->foreignId('produit_id');
            $table->integer('quantite')->default(1);
            $table->timestamps();
        });
    }

    public function down(): void
    {
        Schema::dropIfExists('pack_produit');
        Schema::dropIfExists('packs');
    }
};
