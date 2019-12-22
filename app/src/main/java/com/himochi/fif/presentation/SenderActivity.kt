package com.himochi.fif.presentation

import android.graphics.drawable.ClipDrawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.telephony.SmsManager
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import com.himochi.fif.R
import com.himochi.fif.data.Friend
import com.himochi.fif.presentation.adapter.FriendsAdapter
import kotlinx.android.synthetic.main.activity_sender.bt_add_action
import kotlinx.android.synthetic.main.activity_sender.bt_search_contact_action
import kotlinx.android.synthetic.main.activity_sender.bt_send_action
import kotlinx.android.synthetic.main.activity_sender.et_name
import kotlinx.android.synthetic.main.activity_sender.et_number
import kotlinx.android.synthetic.main.activity_sender.rv_friends
import kotlinx.android.synthetic.main.activity_sender.tv_error
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class SenderActivity : AppCompatActivity(), SenderView {

    private val presenter: SenderPresenter by inject { parametersOf(this) }

    private val textWatcher = object: TextWatcher{
        override fun afterTextChanged(p0: Editable?) { }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            hideErrorFriend()
        }

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sender)
        presenter.start()
    }

    override fun setUpListeners() {
        et_name.addTextChangedListener(textWatcher)
        et_number.addTextChangedListener(textWatcher)
        bt_add_action.setOnClickListener {
            presenter.onAddFriendSelected(Friend(et_name.text.toString(), et_number.text.toString(), false, ""))
        }
        bt_search_contact_action.setOnClickListener {
            Toast.makeText(this, "busqueda de contacto", Toast.LENGTH_SHORT).show()
        }
        bt_send_action.setOnClickListener {
            presenter.onSendSelected()
        }
    }

    override fun renderFriends(friends: List<Friend>) {
        rv_friends.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rv_friends.addItemDecoration(DividerItemDecoration(this, ClipDrawable.HORIZONTAL))
        rv_friends.adapter = FriendsAdapter(friends)
    }

    override fun showErrorFriend() {
        tv_error.visibility = View.VISIBLE
    }

    override fun hideErrorFriend() {
        tv_error.visibility = View.GONE
    }

    override fun updateFriends(friends: List<Friend>) {
        rv_friends.adapter = FriendsAdapter(friends)
    }

    override fun clearFriend() {
        et_name.text.clear()
        et_number.text.clear()
        et_name.requestFocus()
    }

    override fun sendMultipleSms(friend: Friend, msg: String) {
        try {
            val smsManager = SmsManager.getDefault()
            val messageList = SmsManager.getDefault().divideMessage(msg)
            smsManager.sendMultipartTextMessage(friend.number, null, messageList, null, null)
        } catch (e: Exception) {
            Log.e("ERROR SENDING SMS", e.message)
        }
    }


}
