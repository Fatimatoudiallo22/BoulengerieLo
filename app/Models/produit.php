<?php
namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Produit extends Model
{
    use HasFactory;
    protected $guarded = [];

    public function categorie()
    {
        return $this->belongsTo(Categorie::class, 'categorie_id');
    }

    public function packs()
    {
        return $this->belongsToMany(Pack::class, 'pack_produit')
                    ->withPivot('quantite')
                    ->withTimestamps();
    }
}
