package ltd.evilcorp.atox.tox

import android.util.Log
import im.tox.tox4j.core.callbacks.ToxCoreEventListener
import im.tox.tox4j.core.enums.ToxConnection
import im.tox.tox4j.core.enums.ToxFileControl
import im.tox.tox4j.core.enums.ToxMessageType
import im.tox.tox4j.core.enums.ToxUserStatus
import ltd.evilcorp.atox.repository.ContactRepository
import ltd.evilcorp.atox.vo.Contact
import java.text.DateFormat
import java.util.*

class ToxEventListener(
    private val contactRepository: ContactRepository
) : ToxCoreEventListener<Int> {
    private var contacts: List<Contact> = ArrayList()

    init {
        contactRepository.getContacts().observeForever {
            contacts = it
        }
    }

    private fun contactByFriendNumber(friendNumber: Int): Contact {
        return contacts.find { it.friendNumber == friendNumber }!!
    }

    override fun friendLosslessPacket(friendNumber: Int, data: ByteArray, state: Int?): Int {
        return Log.e("ToxCore", "friendLosslessPacket")
    }

    override fun fileRecvControl(friendNumber: Int, fileNumber: Int, control: ToxFileControl, state: Int?): Int {
        return Log.e("ToxCore", "fileRecvControl")
    }

    override fun friendStatusMessage(friendNumber: Int, message: ByteArray, state: Int?): Int {
        with(contactByFriendNumber(friendNumber)) {
            this.statusMessage = String(message)
            contactRepository.addContact(this)
        }

        return Log.e("ToxCore", "friendStatusMessage")
    }

    override fun friendReadReceipt(friendNumber: Int, messageId: Int, state: Int?): Int {
        return Log.e("ToxCore", "friendReadReceipt")
    }

    override fun friendStatus(friendNumber: Int, status: ToxUserStatus, state: Int?): Int {
        with(contactByFriendNumber(friendNumber)) {
            this.status = status.toUserStatus()
            contactRepository.addContact(this)
        }

        return Log.e("ToxCore", "friendStatus")
    }

    override fun friendConnectionStatus(friendNumber: Int, connectionStatus: ToxConnection, state: Int?): Int {
        with(contactByFriendNumber(friendNumber)) {
            this.connectionStatus = connectionStatus.toConnectionStatus()
            contactRepository.addContact(this)
        }

        return Log.e("ToxCore", "friendConnectionStatus")
    }

    override fun friendRequest(publicKey: ByteArray, timeDelta: Int, message: ByteArray, state: Int?): Int {
        return Log.e("ToxCore", "friendRequest")
    }

    override fun friendMessage(
        friendNumber: Int,
        messageType: ToxMessageType,
        timeDelta: Int,
        message: ByteArray,
        state: Int?
    ): Int {
        with(contactByFriendNumber(friendNumber)) {
            lastMessage = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT).format(Date())
            contactRepository.addContact(this)
        }

        return Log.e("ToxCore", "friendMessage")
    }

    override fun friendName(friendNumber: Int, name: ByteArray, state: Int?): Int {
        with(contactByFriendNumber(friendNumber)) {
            this.name = String(name)
            contactRepository.addContact(this)
        }

        return Log.e("ToxCore", "friendName")
    }

    override fun fileRecvChunk(friendNumber: Int, fileNumber: Int, position: Long, data: ByteArray, state: Int?): Int {
        return Log.e("ToxCore", "fileRecvChunk")
    }

    override fun fileRecv(
        friendNumber: Int,
        fileNumber: Int,
        kind: Int,
        fileSize: Long,
        filename: ByteArray,
        state: Int?
    ): Int {
        return Log.e("ToxCore", "fileRecv")
    }

    override fun friendLossyPacket(friendNumber: Int, data: ByteArray, state: Int?): Int {
        return Log.e("ToxCore", "friendLossyPacket")
    }

    override fun selfConnectionStatus(connectionStatus: ToxConnection, state: Int?): Int {
        return Log.e("ToxCore", "selfConnectionStatus")
    }

    override fun friendTyping(friendNumber: Int, isTyping: Boolean, state: Int?): Int {
        with(contactByFriendNumber(friendNumber)) {
            typing = isTyping
            contactRepository.addContact(this)
        }

        return Log.e("ToxCore", "friendTyping")
    }

    override fun fileChunkRequest(friendNumber: Int, fileNumber: Int, position: Long, length: Int, state: Int?): Int {
        return Log.e("ToxCore", "fileChunkRequest")
    }
}