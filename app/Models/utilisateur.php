<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;
use Illuminate\Notifications\Notifiable;
use Laravel\Sanctum\HasApiTokens;
use App\Http\Controllers\AuthController;
class utilisateur extends Model
{
    use  HasApiTokens,HasFactory, Notifiable;
    protected $guarded = [];
    protected $hidden = ['password', 'remember_token'];
  public function commandes()
{
    return $this->hasMany(Commande::class, 'utilisateur_id');
}

public function messagesEnvoyes()
{
    return $this->hasMany(Messagerie::class, 'expediteur_id');
}

public function messagesRecus()
{
    return $this->hasMany(Messagerie::class, 'destinataire_id');
}

public function livraisons()
{
    return $this->hasMany(Livraison::class, 'employe_id');
}

    
}
