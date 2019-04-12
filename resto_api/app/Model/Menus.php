<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Menus extends Model
{
    protected $table = 'menus';

    public function restaurants() {
       return $this->hasMany(Restaurants::class);
    }
}