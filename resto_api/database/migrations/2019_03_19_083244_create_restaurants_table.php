<?php

use Illuminate\Support\Facades\Schema;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class CreateRestaurantsTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('restaurants', function (Blueprint $table) {
            $table->increments('id');
            $table->string('name');
            $table->string('description');
            $table->integer('note')->nullable();
            $table->string('localisation');
            $table->string('téléphone')->nullable();
            $table->string('web_site')->nullable();
            $table->string('horaire_semaine')->nullable();
            $table->string('horaire_week')->nullable();
            $table->Integer('id_user')->unsigned();
            $table->timestamps();
        });

        Schema::table('restaurants', function (Blueprint $table) {
            $table->foreign('id_user')->references('id')->on('users');
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('restaurants');
    }
}
