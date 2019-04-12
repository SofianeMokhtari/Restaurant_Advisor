<?php

namespace App\Http\Controllers;

use App\User;
use Illuminate\Support\Facades\Validator;

use Illuminate\Http\Request;

class RegisterController extends Controller
{
    public function inscription(Request $request) {

        $verification = Validator::make($request->all(), [
            'username' =>'required|string|unique:users',
            'nom' =>'required|string',
            'prénom' =>'required|string',
            'age' => 'required|numeric',
            'telephone' => 'required|string|regex:/(06)[0-9]{8}/',
            'email' => 'required|email|unique:users',
            'password' => 'required|string',
            'password_conf' => 'required|string|same:password'
        ]);

        if ($verification->fails())
            return response()->json($verification->errors(), 403);

        $user = new User();
        $user->username = $request->get('username');
        $user->nom = $request->get('nom');
        $user->prénom = $request->get('prénom');
        $user->age = $request->get('age');
        $user->telephone = $request->get('telephone');
        $user->email = $request->get('email');
        $user->password = bcrypt($request->get('password'));
        $user->save();

        return response()->json([
            'message' => 'Success'
        ]);
    }
}