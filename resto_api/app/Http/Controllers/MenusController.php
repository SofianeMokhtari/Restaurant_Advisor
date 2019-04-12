<?php

namespace App\Http\Controllers;

use App\Menus;
use App\Restaurants;
use App\User;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Validator;

class MenusController extends Controller
{
    public function createMenus(Request $request) {
        
        $verification = Validator::make($request->all(), [
            'name' => 'string|unique:menus',
            'description' => 'required|string',
            'prix' => 'required|numeric',
            'id' => 'numeric|exists:restaurants',
        ]);

        

        if ($verification->fails())
            return response()->json([
                'required' => 'not found',
            ], 403);
        

        $if_exist = Restaurants::where([['id_user', $request->user()->id], ['id', $request->get('id')]])->first();
        if (!$if_exist) {
            return response()->json([
                'Unauthorized' => 'voleur',
            ], 403);
        }
    

        $menu = new Menus();
        $menu->name = $request->get('name');
        $menu->description = $request->get('description');
        $menu->prix = $request->get('prix');
        $menu->id_restaurants = $request->get('id');
        $menu->id_user = $request->user()->id;
        $menu->save();

        $this->restau_prix($request->get('id'));
        return response()->json([
            'message' => 'Success'
        ], 200);
    }

    public function updateMenus(Request $request) {

        $verification = Validator::make($request->all(), [
            'name' => 'string|unique:menus',
            'description' => 'string',
            'prix' => 'numeric',
            'id_restaurants' => 'numeric|exists:menus',
            'id' => 'numeric|exists:menus'
        ]);

        if ($verification->fails())
            return response()->json([
                'required' => 'not found',
            ], 403);

        $input = array_filter($request->all());

        Menus::where([['id_user', $request->user()->id], ['id_restaurants', $request->get('id_restaurants')], ['id', $request->get('id')]])->update($input);

        return response()->json([
            'message' => 'Success'
        ], 200);
    }
   public function deleteMenus(Request $request) {

        $verification = Validator::make($request->all(), [
            'id_restaurants' => 'numeric|exists:menus',
            'id' => 'numeric|exists:menus'
        ]);

        if ($verification->fails())
        return response()->json($verification->errors(), 403);

        Menus::where([['id_user', $request->user()->id], ['id_restaurants', $request->get('id_restaurants')], ['id', $request->get('id')]])->delete();

        return response()->json([
            'message' => 'Success'
        ]);
    }

    public function restau_prix() {
        $restaurant = Restaurants::get();
        $i = 0;

        foreach($restaurant as $table) {
            $restau_prix[$i] = [
            'id' => $table->id,
            'avg' => Menus::where('id_restaurants', $table->id)->avg('prix')
            ];
            $i++;
        }

        $restau_prix = $this->del_null($restau_prix);
        $restau_prix = $this->tri($restau_prix);

        $i = 0;

        foreach($restau_prix as $ok) {
            $okmani[$i] = Restaurants::where('id', $ok["id"])->get();
            $i++;
        }

        return $okmani;
    }

    public function del_null($tab) {
        $i = 0;
        foreach($tab as $r) {
            if ($r["avg"] == null) {
                $tab[$i]["avg"] = "0";
            }
            $i++;
        }
        return $tab;
    }

    public function tri($tab) {

        for ($i=0; $i < sizeof($tab) -1; $i++) {
            for ($j=0; $j < sizeof($tab) -1; $j++) {
                if ((float)$tab[$j]["avg"] < (float)$tab[$j + 1]["avg"]) {
                    $stock = $tab[$j];
                    $tab[$j] = $tab[$j + 1];
                    $tab[$j + 1] = $stock;
                }
            }
        }
        return $tab;
    }
}