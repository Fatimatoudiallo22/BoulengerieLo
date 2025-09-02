<?php
use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

return new class extends Migration
{
    public function up(): void
    {
        Schema::create('messageries', function (Blueprint $table) {
            $table->id();
            $table->foreignId('expediteur_id')->constrained('utilisateurs')->onDelete('cascade');
            $table->foreignId('destinataire_id')->constrained('utilisateurs')->onDelete('cascade');
            $table->text('message');
            $table->timestamp('lu_le')->nullable();
            $table->timestamps();
        });
    }

    public function down(): void
    {
        Schema::dropIfExists('messageries');
    }
};
