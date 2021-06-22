package com.example.allamvizsga_2020_21.Firebase.Data

data class CameraSetting(
    var face: Boolean=true,
    var emotion: Boolean=true,
    var voice: Boolean=true,
    var violence: Boolean=true
) {
}