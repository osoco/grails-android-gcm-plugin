package es.osoco.android.gcm

import java.util.List;
import java.util.Map;

import com.google.android.gcm.server.Message
import com.google.android.gcm.server.MulticastResult
import com.google.android.gcm.server.Result
import com.google.android.gcm.server.Sender

import es.osoco.android.gcm.exception.ApiKeyNotFoundException

class AndroidGcmService {

    static transactional = false

	def grailsApplication

	/**
	 * Sends a message by invoking the appropriate send method depending on the parameters
	 * @param data The message
	 * @param registrationIds The devices registration ids
	 * @param collapseKey the collapse key (by default is empty)
	 * @param multicast whether the message should be sent as a multicast message or not 
	 * (by default it will be true if more than one registration id is passed)  
	 * @return
	 */
	def sendMessage(Map data, List<String> registrationIds, String collapseKey = '', boolean multicast = registrationIds.size() > 0)
	{
		def method = "send${multicast ? 'Multicast' : ''}${collapseKey ? 'Collapse' : 'Instant'}Message"
		def params = (collapseKey ? [collapseKey] : []) + [data] + (multicast ?
			[registrationIds] : [registrationIds[0]])
		this.invokeMethod(method, params.toArray())
	}
		
    def Result sendCollapseMessage(String collapseKey, Map data, String registrationId) {
        sender().send(buildCollapseMessage(collapseKey, data), registrationId, retries())
    }

    def MulticastResult sendMulticastCollapseMessage(String collapseKey, Map data, List<String> registrationIds) {
        sender().send(buildCollapseMessage(collapseKey, data), registrationIds, retries())
    }

    def Result sendInstantMessage(Map data, String registrationId) {
        sender().send(buildInstantMessage(data), registrationId, retries())
    }

    def MulticastResult sendMulticastInstantMessage(Map data, List<String> registrationIds) {
        sender().send(buildInstantMessage(data), registrationIds, retries())
    }

    private Message buildCollapseMessage(String collapseKey, Map data) {
        Message.Builder messageBuilder = new Message.Builder()
            .collapseKey(collapseKey)
            .timeToLive(timeToLive())
            .delayWhileIdle(delayWhileIdle())

        data.each {
            messageBuilder.addData(it.key, it.value)
        }

        messageBuilder.build()
    }

    private Message buildInstantMessage(Map data) {
        Message message = new Message.Builder()
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