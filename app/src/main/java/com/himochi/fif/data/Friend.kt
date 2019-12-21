package com.himochi.fif.data

data class Friend(
        var name: String,
        var number: String,
        var used: Boolean,
        var assigned: String,
        var algorithmType: Int = 0
)