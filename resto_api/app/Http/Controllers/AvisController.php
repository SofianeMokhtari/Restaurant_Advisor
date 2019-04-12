<?php

namespace App\Http\Controllers;

use App\Avis;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Validator;
use App\Restaurants;


class AvisController extends Controller
{
    public function createAvis(Request $request) {

        $verification = Validator::make($request->all(), [
            'description' => 'required:string',
            'note' => 'required:integer:max:10',
            'id' => 'numeric|exists:restaurants',
        ]);

        if ($verification->fails())
            return response()->json($verification->errors(), 403);

        $avis = new Avis();
        $avis->description = $request->get('description');
        $avis->note = $request->get('note');
        $avis->id_user = $request->user()->id;
        $avis->id_restaurants = $request->get('id');
        $avis->save();
        
        $this->updateNote($request->get('id'));
        
        return response()->json([
            'message' => 'Success'
        ]);

        
    }

    public function deleteAvis(Request $request) {

        $verification = Validator::make($request->all(), [
            'id' => 'numeric|exists:avis',
        ]);

        if ($verification->fails())
            return response()->json([
                'required' => 'not found',
            ]);

        Avis::where([['id_user', $request->user()->id], ['id', $request->get('id')]])->delete();

        return response()->json([
            'message' => 'Success'
        ]);
    }

    public function updateNote($id) {
        $all_note = Avis::where('id_restaurants', $id)->avg('note');
        Restaurants::where('id', $id)->update(['note' => $all_note]);
    }

}