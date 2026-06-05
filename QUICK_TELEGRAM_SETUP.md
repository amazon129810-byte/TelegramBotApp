# ⚡ QUICK TELEGRAM BOT CONNECTION GUIDE

## 🚀 5-MINUTE SETUP

### Step 1: Create Bot (1 minute)
```
1. Open Telegram
2. Search @BotFather
3. Send /newbot
4. Name: "My App Bot"
5. Username: "my_app_bot"
6. Copy token: 123456789:ABCDefGHIjklmnoPQRstuvWXYZabcDefG
```

### Step 2: Add Token to App (1 minute)
```properties
# local.properties
TELEGRAM_BOT_TOKEN=123456789:ABCDefGHIjklmnoPQRstuvWXYZabcDefG
```

### Step 3: Build & Run (2 minutes)
```bash
./gradlew assembleDebug
adb install app/build/outputs/apk/debug/app-debug.apk
```

### Step 4: Test (1 minute)
- Open app
- See connection test result
- Send message to bot in Telegram
- Message appears in app ✅

---

## 📝 CODE IMPLEMENTATION

### Send Message to Telegram

```kotlin
// In ChatViewModel.kt
fun sendToTelegram(chatId: Long, message: String) {
    viewModelScope.launch {
        try {
            val request = SendMessageRequest(
                chatId = chatId,
                text = message
            )
            
            telegramBotService.sendMessage(
                botToken = BuildConfig.TELEGRAM_BOT_TOKEN,
                request = request
            )
        } catch (e: Exception) {
            _error.value = e.message
        }
    }
}
```

### Receive Messages from Telegram

```kotlin
// In BotSyncWorker.kt
val response = telegramBotService.getUpdates(
    botToken = BuildConfig.TELEGRAM_BOT_TOKEN,
    offset = offset + 1,
    timeout = 30
)

response.result?.forEach { update ->
    update.message?.let { msg ->
        messageDao.insert(
            MessageEntity(
                botId = "default_bot",
                content = msg.text,
                sender = msg.from?.firstName ?: "Bot",
                timestamp = msg.date * 1000L
            )
        )
    }
}
```

---

## 🔑 GET YOUR BOT TOKEN

### Via @BotFather

```
1. @BotFather → /mybots
2. Select your bot
3. Tap "API Token"
4. Token appears
```

### Format
```
123456789:ABCDefGHIjklmnoPQRstuvWXYZabcDefG
                ↑                          ↑
            Bot ID                   Token Secret
```

---

## 🧪 VERIFY CONNECTION

### Test in Terminal

```bash
# Replace YOUR_TOKEN and YOUR_CHAT_ID
TOKEN="123456789:ABCDefGHIjklmnoPQRstuvWXYZabcDefG"
CHAT_ID="987654321"

# Get bot info (verify token)
curl "https://api.telegram.org/bot${TOKEN}/getMe"

# Get messages
curl "https://api.telegram.org/bot${TOKEN}/getUpdates"

# Send test message
curl -X POST "https://api.telegram.org/bot${TOKEN}/sendMessage" \
  -d "chat_id=${CHAT_ID}" \
  -d "text=Test from API"
```

### Test in App

Add to `MainActivity.onCreate()`:

```kotlin
lifecycleScope.launch {
    val response = telegramBotService.getMe(
        botToken = BuildConfig.TELEGRAM_BOT_TOKEN
    )
    
    if (response.ok) {
        Timber.d("✅ Bot connected: ${response.result?.get(0)?.username}")
    } else {
        Timber.e("❌ Connection failed: ${response.description}")
    }
}
```

Check logs:
```bash
adb logcat | grep "✅\|❌"
```

---

## 📱 WORKFLOW

```
Your Bot in Telegram
        ↓
    [Message]
        ↓
  Telegram API (getUpdates)
        ↓
  TelegramBotService (Retrofit)
        ↓
  BotSyncWorker (Background)
        ↓
  Room Database
        ↓
  ChatFragment (UI)
        ↓
    [Display]
        
    [User types reply]
        ↓
  SendMessage via API
        ↓
  Telegram Bot
        ↓
  Telegram App (user sees response)
```

---

## 🔄 POLLING VS WEBHOOK

### POLLING (Default - What Your App Uses)
```kotlin
// Repeatedly ask for new messages
while (true) {
    getUpdates(offset)
    delay(1000) // Check every second
}
```
✅ Works everywhere  
✅ No server needed  
❌ Uses more battery  

### WEBHOOK (Advanced)
```kotlin
// Telegram sends messages to your server
POST /webhook
{
  "update_id": 123,
  "message": { ... }
}
```
✅ Real-time  
✅ Less battery  
❌ Needs server  

**Your app uses POLLING by default** ✅

---

## 💡 COMMON OPERATIONS

### Send Message with Formatting
```kotlin
val request = SendMessageRequest(
    chatId = 987654321,
    text = "<b>Bold</b> and <i>italic</i>",
    parseMode = "HTML"  // or "Markdown"
)
```

### Auto-Reply
```kotlin
// Listen for incoming messages
messageDao.getMessagesByBot("default_bot").collect { messages ->
    messages.forEach { msg ->
        if (!msg.isSynced) {
            // Auto-reply
            sendMessage(msg.botId, msg.timestamp.toLong(), "Got it: ${msg.content}")
        }
    }
}
```

### Handle Commands
```kotlin
when {
    text.startsWith("/start") -> "Welcome!"
    text.startsWith("/help") -> "Available commands: /start, /help, /status"
    text.startsWith("/status") -> "Everything working!"
    else -> "Echo: $text"
}
```

---

## ⚠️ COMMON ISSUES & FIXES

| Issue | Solution |
|-------|----------|
| "Invalid token" | Check format: `ID:SECRET` (has colon) |
| "Chat not found" | Use correct `chat_id` (number, not string) |
| "Bot blocked" | User must unblock your bot |
| No messages | Send test message to bot in Telegram first |
| Slow responses | Normal polling delay is 1-3 seconds |
| Connection timeout | Check internet permission in manifest |

---

## 📊 YOUR BOT FLOW

```
1. User sends: "Hello"
   ↓
2. Telegram stores message
   ↓
3. Your app polls: "Any new messages?"
   ↓
4. Telegram API returns: [Message from user]
   ↓
5. App saves to database
   ↓
6. UI displays message
   ↓
7. User types reply: "Hi there!"
   ↓
8. App sends via API: SendMessage
   ↓
9. Telegram receives and notifies user
   ↓
10. User sees reply in Telegram
```

---

## 🎯 WHAT'S ALREADY BUILT FOR YOU

✅ API DTOs (Message, Chat, User, etc.)  
✅ Retrofit Service (TelegramBotService)  
✅ Background Worker (BotSyncWorker)  
✅ Database (MessageEntity)  
✅ UI (ChatFragment, MessageAdapter)  
✅ ViewModel (ChatViewModel)  
✅ Error Handling  
✅ Logging  

**You only need to:**
1. Add your bot token
2. Build and run
3. Test with Telegram

---

## 🚀 NEXT STEPS

1. **Get token from @BotFather**
   ```
   @BotFather → /newbot → Copy token
   ```

2. **Add to local.properties**
   ```properties
   TELEGRAM_BOT_TOKEN=your_token_here
   ```

3. **Build & Deploy**
   ```bash
   ./gradlew assembleDebug
   ```

4. **Test**
   - Open app
   - Send message to bot in Telegram
   - See it in app ✅

5. **Deploy to Production**
   ```bash
   ./gradlew bundleRelease
   # Upload to Google Play Store
   ```

---

## 📞 NEED HELP?

- **Full Guide**: See `TELEGRAM_BOT_INTEGRATION_GUIDE.md`
- **Code Issues**: Check `SETUP_GUIDE.md`
- **Telegram Docs**: https://core.telegram.org/bots/api
- **BotFather**: @BotFather in Telegram

---

**Status**: ✅ Ready to Connect!
