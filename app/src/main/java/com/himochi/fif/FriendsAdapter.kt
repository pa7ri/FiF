package com.himochi.fif

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_friend.view.*

class FriendsAdapter(private var data: List<Friend>)
    : RecyclerView.Adapter<FriendsAdapter.FriendViewHolder>() {
    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendViewHolder {
        return FriendViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_friend, parent, false))
    }

    override fun onBindViewHolder(holder: FriendViewHolder, position: Int) {
        holder.bind(data[position])
    }

    inner class FriendViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(friend: Friend) = with(itemView) {
            tv_name_friend.text = friend.name
            tv_number_friend.text = friend.number
        }
    }
}