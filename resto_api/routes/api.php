<?php

use Illuminate\Http\Request;

/*
|--------------------------------------------------------------------------
| API Routes
|--------------------------------------------------------------------------
|
| Here is where you can register API routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| is assigned the "api" middleware group. Enjoy building your API!
|
*/

Route::middleware('auth:api')->get('/user', function (Request $request) {
    return $request->user();
});

 Route::post('/inscription', 'RegisterController@inscription');
 Route::post('/login', 'LoginController@login');
 Route::get('/error', 'LoginController@error')->name('error');
 Route::get('/userlist', 'InfosController@list');
 Route::get('/restaulist', 'InfosController@listrestau');
 Route::get('/restaurant/{id}', 'InfosController@id_restaurant');
 Route::get('/menu/{id}', 'InfosController@menu');
 Route::get('/orderbydesc', 'InfosController@restau_note');
 Route::get('/restaurecent', 'InfosController@restau_recent');
 Route::get('/restauprix', 'MenusController@restau_prix');
 Route::get('/restaurantn/{name}', 'InfosController@name_restaurant');

Route::group([
    'middleware' => 'auth:api'
], function() {
    Route::get('logout', 'LoginController@logout');
    Route::post('/createRestaurants', 'RestaurantsController@createRestaurant');
    Route::post('/deleteRestaurants', 'RestaurantsController@deleteRestaurant');
    Route::post('/updateRestaurants', 'RestaurantsController@updateRestaurant');
    Route::post('/createMenus', 'MenusController@createMenus');
    Route::post('/deleteMenus', 'MenusController@deleteMenus');
    Route::post('/updateMenus', 'MenusController@updateMenus');
    Route::post('/createAvis', 'AvisController@createAvis');
    Route::post('/deleteAvis', 'AvisController@deleteAvis');
});
