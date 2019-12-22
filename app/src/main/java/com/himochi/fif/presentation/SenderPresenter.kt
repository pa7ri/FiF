package com.himochi.fif.presentation

import com.himochi.fif.data.AlgorithmType
import com.himochi.fif.data.Friend
import com.himochi.fif.domain.GetEncryptedUseCase
import java.util.*

class SenderPresenter(
        private val view: SenderView,
        private val getEncryptedUseCase: GetEncryptedUseCase
) {

    private val friends: MutableList<Friend> = mutableListOf()
    private val algorithmList: List<Pair<AlgorithmType, String>> = listOf(
            Pair(AlgorithmType.MD5, "-- -.. ....."),
            Pair(AlgorithmType.SHA1, "... .... .- .----"),
            Pair(AlgorithmType.SHA256, "... .... .- ..--- ..... -...."))

    fun start() {
        view.setUpListeners()
        view.renderFriends(friends)
    }

    fun onAddFriendSelected(friend: Friend) {
        if (friend.name.isNotEmpty() && friend.number.isNotEmpty()) {
            view.hideErrorFriend()
            friends.add(friend)
            view.renderFriends(friends)
        } else {
            view.showErrorFriend()
        }
    }

    fun onSendSelected() {
        assignRandomFriend()
        friends.forEach {
            view.sendMultipleSms(it, buildMessage(it))
        }
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
                friend.algorithmType = Random().nextInt(algorithmList.size)
                friend.assigned = getEncryptedName(friends[randomAssignation].name, algorithmList[friend.algorithmType].first)
                friends[randomAssignation].used = true
            }
        }
    }

    private fun getEncryptedName(name: String, alg: AlgorithmType): String {
        return when (alg) {
            AlgorithmType.MD5 -> getEncryptedUseCase.getEncripted(name, "MD5")
            AlgorithmType.SHA1 -> getEncryptedUseCase.getEncripted(name, "SHA-1")
            AlgorithmType.SHA256 -> getEncryptedUseCase.getEncripted(name, "SHA-256")
        }
    }

    private fun buildMessage(friend: Friend): String = "Hi " + friend.name + ", \n " +
            "I hope you enjoy this FiF, but there is nothing easy in this life, this either \uD83E\uDD17  \n " +
            " 1. Your assigned friend is : " + friend.assigned + ". \n " +
            " 2. As you can see it isn't legible...IT'S AN ENCRYPTED CODE! Yas, like Da Vinci \uD83D\uDE0E Now you have to guess which algorithm is behind, reversing this morse  \n " +
            algorithmList[friend.algorithmType].second + "\n " +
            " 3. Almost there! Now you've got your message and algorithm, go to any magic browser and decrypt it. \n \n" +
            "Adventure time! 🕵️‍♀️ 😽 \n  ---  https://gph.is/2HPEMQz   --- \n \n" +
            "HINT: Just if you feel desperate, DON'T CHEAT! Oki, just a little \uD83D\uDE08 `https://hashtoolkit.com/reverse-hash` copy&paste your code \n \n" +
            "    \uD83D\uDE18 \uD83D\uDE18 \uD83D\uDE18 Love you! \uD83D\uDE18 \uD83D\uDE18 \uD83D\uDE18"


}