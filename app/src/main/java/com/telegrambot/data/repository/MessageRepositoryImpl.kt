package com.telegrambot.data.repository

import com.telegrambot.data.local.dao.MessageDao
import com.telegrambot.data.local.entity.MessageEntity
import com.telegrambot.data.remote.service.TelegramBotService
import com.telegrambot.domain.model.Message
import com.telegrambot.domain.repository.MessageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import timber.log.Timber
import javax.inject.Inject

class MessageRepositoryImpl @Inject constructor(
    private val messageDao: MessageDao,
    private val telegramBotService: TelegramBotService
) : MessageRepository {

    override suspend fun sendMessage(botId: String, chatId: Long, text: String): Result<Message> {
        return try {
            // For now, we'll store locally
            val messageEntity = MessageEntity(
                botId = botId,
                content = text,
                sender = "user"
            )
            val id = messageDao.insert(messageEntity)
            Result.success(
                Message(
                    id = id,
                    botId = botId,
                    content = text,
                    sender = "user",
                    timestamp = System.currentTimeMillis()
                )
            )
        } catch (e: Exception) {
            Timber.e(e, "Error sending message")
            Result.failure(e)
        }
    }

    override fun getMessagesByBot(botId: String): Flow<List<Message>> {
        return messageDao.getMessagesByBot(botId).map { entities ->
            entities.map { entity ->
                Message(
                    id = entity.id,
                    botId = entity.botId,
                    content = entity.content,
                    sender = entity.sender,
                    timestamp = entity.timestamp,
                    isRead = entity.isRead
                )
            }
        }
    }

    override suspend fun syncMessages(botId: String): Result<Unit> {
        return try {
            val unsynced = messageDao.getUnsyncedMessages()
            Timber.d("Syncing ${unsynced.size} unsynced messages")
            
            if (unsynced.isNotEmpty()) {
                messageDao.markMessagesSynced(unsynced.map { it.id })
            }
            
            Result.success(Unit)
        } catch (e: Exception) {
            Timber.e(e, "Error syncing messages")
            Result.failure(e)
        }
    }

    override suspend fun deleteOldMessages(botId: String, beforeTimestamp: Long): Result<Unit> {
        return try {
            messageDao.deleteMessagesOlderThan(beforeTimestamp)
            Result.success(Unit)
        } catch (e: Exception) {
            Timber.e(e, "Error deleting old messages")
            Result.failure(e)
        }
    }
}
