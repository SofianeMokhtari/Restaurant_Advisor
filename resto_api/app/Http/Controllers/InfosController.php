<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Restaurants;
use App\User;
use App\Menus;


class InfosController extends Controller
{
    public function list() {
        $users_list = Users::get();
        return response()
    ->json($users_list, 200, ['Content-type'=> 'application/json; charset=utf-8'], JSON_UNESCAPED_UNICODE);
    }

    public function listrestau() {
        $restau_list = Restaurants::get();
        return response()
    ->json($restau_list, 200, ['Content-type'=> 'application/json; charset=utf-8'], JSON_UNESCAPED_UNICODE);
    }

    public function id_restaurant($id) {
        $id_restaurant =  Restaurants::where('id', $id)->get();
        return response()
    ->json($id_restaurant, 200, ['Content-type'=> 'application/json; charset=utf-8'], JSON_UNESCAPED_UNICODE);
    }

    public function name_restaurant($name) {
        $name_restaurant =  Restaurants::where('name', $name)->get();
        return response()
    ->json($name_restaurant, 200, ['Content-type'=> 'application/json; charset=utf-8'], JSON_UNESCAPED_UNICODE);
    }

    public function menu($id) {
        $id_menu = Menus::where('id_restaurants', $id)->get();
        return response()
    ->json($id_menu, 200, ['Content-type'=> 'application/json; charset=utf-8'], JSON_UNESCAPED_UNICODE);
    }

    public function restau_note() {
        $restau_note = Restaurants::orderBy('note', 'DESC')->get();
        return response()
    ->json($restau_note, 200, ['Content-type'=> 'application/json; charset=utf-8'], JSON_UNESCAPED_UNICODE);
    }

    public function restau_recent() {
        $restau_recent = Restaurants::orderBy('created_at', 'DESC')->get();
        return response()
    ->json($restau_recent , 200, ['Content-type'=> 'application/json; charset=utf-8'], JSON_UNESCAPED_UNICODE);
    }

}
