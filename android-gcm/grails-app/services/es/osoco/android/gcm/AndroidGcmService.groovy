package es.osoco.android.gcm

import com.google.android.gcm.server.Message
import com.google.android.gcm.server.Result
import com.google.android.gcm.server.Sender

import es.osoco.android.gcm.exception.ApiKeyNotFoundException

class AndroidGcmService {

    static transactional = false

	def grailsApplication

    def Result sendCollapseMessage(String collapseKey, Map data, List<String> registrationIds) {
        Message message = buildMessage(collapseKey, data)

        registrationIds.each {
            sender().send(message, registrationId, retries())
        }
    }

    def Result send(Message message, String registrationId, int retries) {
        sender().send(message, registrationId, retries)
    }

    private Message buildMessage(String collapseKey, Map data) {
        Message message = new Message.Builder()
            .collapseKey(collapseKey)
            .timeToLive(timeToLive())
            .delayWhileIdle(delayWhileIdle())

        data.each {
            message.addData(it.key, it.value)
        }

        message.build()
    }

    private sender() {
        new Sender(apiKey());
    }

    private apiKey() {
        def key = grailsApplication.config.android.gcm.api.key
        if(!key) {
            throw new ApiKeyNotFoundException(grailsApplication.config.api.key.config.property.name)
        }
        key
    }

    private timeToLive() {
        grailsApplication.config.android.gcm.time.to.live
    }

    private delayWhileIdle() {
        grailsApplication.config.android.gcm.delay.'while'.idle
    }

    private retries() {
        grailsApplication.config.android.gcm.retries
    }
}