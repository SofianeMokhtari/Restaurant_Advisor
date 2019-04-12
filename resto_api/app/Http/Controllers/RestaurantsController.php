<?php

namespace App\Http\Controllers;

use App\Restaurants;
use App\Menus;
use Illuminate\Support\Facades\Validator;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;

class RestaurantsController extends Controller
{
    public function createRestaurant(Request $request) {

        $verification = Validator::make($request->all(), [
            'name' => 'required|string|unique:restaurants',
            'note' => 'integer|max:10',
            'téléphone' => 'required|string|regex:/(06)[0-9]{8}/',
            'description' => 'string',
            'localisation' => 'required|string|unique:restaurants',
            'web_site' => 'string',
            'horaire_semaine' => 'string',
            'horaire_week' => 'string'
        ]);

    if ($verification->fails())
          return response()->json($verification->errors(), 403);

        $restaurand = new Restaurants();
        $restaurand->name = $request->get('name');
        $restaurand->description = $request->get('description');
       // $restaurand->note = $request->get('note');
        $restaurand->localisation = $request->get('localisation');
        $restaurand->téléphone = $request->get('téléphone');
        $restaurand->web_site = $request->get('web_site');
        $restaurand->horaire_semaine = $request->get('horaire_semaine');
        $restaurand->horaire_week = $request->get('horaire_week');
        $restaurand->id_user = $request->user()->id;
        $restaurand->save();

        return response()->json([
            'message' => 'Success'
        ], 200);
    }
    public function deleteRestaurant(Request $request) {

        $verification = Validator::make($request->all(), [
            'id' => 'required|numeric|exists:restaurants',
        ]);

        if ($verification->fails())
            return response()->json([
                'required' => 'not found',
            ], 403);
        
        Menus::where('id_restaurants', $request->get('id'))->delete();
        Avis::where('id_restaurants', $request->get('id'))->delete();
        Restaurants::where([['id_user', $request->user()->id], ['id', $request->get('id')]])->delete();

        return response()->json([
            'message' => 's=Success'
        ], 200);
    }
   public function updateRestaurant(Request $request) {

        $verification = Validator::make($request->all(), [
            'name' => 'nullable|string|unique:restaurants',
           // 'note' => 'nullable|string',
            'téléphone' => 'nullable|string',
            'description' => 'nullable|string',
            'localisation' => 'nullable|required|string|unique:restaurants',
            'web_site' => 'nullable|string',
            'horaire_semaine' => 'nullable|string',
            'horaire_week' => 'nullable|string',
            'id' => 'numeric|exists:restaurants',
        ]);

        if ($verification->fails())
            return response()->json([
                'required' => 'not found',
            ], 403);
        $input = array_filter($request->all());

        Restaurants::where([['id_user', $request->user()->id], ['id', $request->get('id')]])->update($input);
        return response()->json([
            'message' => 'Success'
        ], 200);
    }
    
}