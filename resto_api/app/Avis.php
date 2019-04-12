<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Avis extends Model
{
    protected $table = 'avis';

    public function restaurants() {
        return $this->hasMany(Restaurants::class);
    }

    public function user() {
        return $this->hasMany(User::class);
    }
}