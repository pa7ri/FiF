package com.himochi.fif

import android.graphics.drawable.ClipDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_sender.*
import java.util.*
import android.util.Log
import android.widget.Toast
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.Manifest.permission.SEND_SMS
import android.telephony.SmsManager


class SenderActivity : AppCompatActivity() {

    enum class AlgorithmType {
        MD5, SHA1, SHA256
    }

    private val friends: MutableList<Friend> = mutableListOf()
    private val algorithList: List<Pair<AlgorithmType, String>> = listOf(
            Pair(AlgorithmType.MD5, "-- -.. ....."),
            Pair(AlgorithmType.SHA1, "... .... .- .----"),
            Pair(AlgorithmType.SHA256, "... .... .- ..--- ..... -...."))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sender)
        requestSmsPermission()

        renderFriends()
        setUpListeners()
    }

    private fun setUpListeners() {
        bt_add_action.setOnClickListener {
            if (tv_name_title.text.isNotEmpty() && tv_number_title.text.isNotEmpty()) {
                friends.add(Friend(et_name.text.toString(), et_number.text.toString(), false, ""))
                et_name.text.clear()
                et_number.text.clear()
                et_name.requestFocus()
                updateFriends()
            }
        }
        bt_send_action.setOnClickListener {
            assignRandomFriend()
            sendMultipleSms()
        }
    }

    private fun renderFriends() {
        rv_friends.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rv_friends.addItemDecoration(DividerItemDecoration(this, ClipDrawable.HORIZONTAL))
        rv_friends.adapter = FriendsAdapter(friends)
    }

    private fun updateFriends() {
        rv_friends.adapter = FriendsAdapter(friends)
    }

    private fun assignRandomFriend() {
        friends.forEachIndexed { i, friend ->
            var randomAssignation = Random().nextInt(friends.size)
            var retryTimes = 5
            while ((i == randomAssignation || friends[randomAssignation].used) && retryTimes > 0) {
                randomAssignation = Random().nextInt(friends.size)
                retryTimes--
            }
            if (retryTimes == 0) {
                friends.forEach { it.used = false }
                assignRandomFriend()
            } else {
                friend.algorithmType = Random().nextInt(algorithList.size)
                friend.assigned = getEncryptedName(friends[randomAssignation].name, algorithList[friend.algorithmType].first)
                friends[randomAssignation].used = true
            }
        }
    }


    private fun getEncryptedName(name: String, alg: AlgorithmType): String {
        return when (alg) {
            AlgorithmType.MD5 -> BuildAlgorithm().getEncripted(name, "MD5")
            AlgorithmType.SHA1 -> BuildAlgorithm().getEncripted(name, "SHA-1")
            AlgorithmType.SHA256 -> BuildAlgorithm().getEncripted(name, "SHA-256")
        }
    }

    private fun sendMultipleSms() {
        friends.forEach {
            try {
                val msg = buildMessage(it)
                val smsManager = SmsManager.getDefault()
                val messageList = SmsManager.getDefault().divideMessage(msg)
                smsManager.sendMultipartTextMessage(it.number, null, messageList, null, null)
            } catch (e: Exception) {
                Log.e("ERROR SENDING SMS", e.message)
            }
        }
    }

    private fun buildMessage(friend: Friend): String = "Hi " + friend.name + ", \n " +
            "I hope you enjoy this FiF, but there is nothing easy in this life, this either \uD83E\uDD17  \n " +
            " 1. Your assigned friend is : " + friend.assigned + ". \n " +
            " 2. As you can see it isn't legible...IT'S AN ENCRYPTED CODE! Yas, like Da Vinci \uD83D\uDE0E Now you have to guess which algorithm is behind, reversing this morse  \n " +
            algorithList[friend.algorithmType].second + "\n " +
            " 3. Almost there! Now you've got your message and algorithm, go to any magic browser and decrypt it. \n \n" +
            "Adventure time! 🕵️‍♀️ 😽 \n  ---  https://gph.is/2HPEMQz   --- \n \n" +
            "HINT: Just if you feel desperate, DON'T CHEAT! Oki, just a little \uD83D\uDE08 `https://hashtoolkit.com/reverse-hash` copy&paste your code \n \n" +
            "    \uD83D\uDE18 \uD83D\uDE18 \uD83D\uDE18 Love you! \uD83D\uDE18 \uD83D\uDE18 \uD83D\uDE18"

    private fun requestSmsPermission() {
        if (ContextCompat.checkSelfPermission(this, SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(SEND_SMS), 1)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Awesome! \ud83d\ude01 We aware sending SMS may cost you some money", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "No SMS will be sent, but you still can see the result", Toast.LENGTH_SHORT).show()
            }
        }
    }

}
