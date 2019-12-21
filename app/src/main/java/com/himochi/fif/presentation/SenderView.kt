package com.himochi.fif.presentation

import com.himochi.fif.data.Friend

interface SenderView {
    fun setUpListeners()
    fun renderFriends(friends: List<Friend>)
    fun showErrorFriend()
    fun hideErrorFriend()
    fun updateFriends(friends: List<Friend>)
    fun clearFriend()
    fun sendMultipleSms(friend: Friend, msg: String)
}