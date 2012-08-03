package es.osoco.android.gcm

import com.google.android.gcm.server.Message
import com.google.android.gcm.server.Result
import com.google.android.gcm.server.Sender

class AndroidGcmService {

    static transactional = false

	def grailsApplication

    def Result sendMessage(Message message, String registrationId, int retries) {
        sender().send(message, registrationId, retries)
    }

    private sender() {
        new Sender(getApiKey());
    }

    private getApiKey() {
        grailsApplication.config.android.gcm.api.key
    }
}
