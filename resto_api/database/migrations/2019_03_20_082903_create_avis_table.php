<?php

use Illuminate\Support\Facades\Schema;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class CreateAvisTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('avis', function (Blueprint $table) {
            $table->Increments('id');
            $table->string('description');
            $table->integer('note');
            $table->integer('id_user')->unsigned();
            $table->integer('id_restaurants')->unsigned();
            $table->timestamps();
        });

        Schema::table('avis', function (Blueprint $table) {
            $table->foreign('id_restaurants')->references('id')->on('restaurants');
        });

        Schema::table('avis', function (Blueprint $table) {
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
        Schema::dropIfExists('avis');
    }
}