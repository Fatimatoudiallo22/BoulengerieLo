<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class promotion extends Model
{
    use HasFactory;

    protected $guarded = [];

    public function produit()
    {
        return $this->belongsTo(Produit::class, 'produit_id');
    }

    public function pack()
    {
        return $this->belongsTo(Pack::class, 'pack_id');
    }
}
