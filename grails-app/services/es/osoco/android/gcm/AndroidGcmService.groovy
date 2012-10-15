package es.osoco.android.gcm

import com.google.android.gcm.server.Message
import com.google.android.gcm.server.MulticastResult
import com.google.android.gcm.server.Result
import com.google.android.gcm.server.Sender

import es.osoco.android.gcm.exception.ApiKeyNotFoundException

class AndroidGcmService {

	static transactional = false

	def grailsApplication

	/**
	 * Sends a collapse message to one device
	 */
	Result sendCollapseMessage(String collapseKey, Map data, String registrationId,
			String apiKey = apiKeyFromConfig()) {
		sendMessage(data, [registrationId], collapseKey, apiKey)
	}

	/**
	 * Sends a collapse message to multiple devices
	 */
	MulticastResult sendMulticastCollapseMessage(String collapseKey, Map data,
			List<String> registrationIds, String apiKey = apiKeyFromConfig()) {
		sendMessage(data, registrationIds, collapseKey, apiKey)
	}

	/**
	 * Sends an instant message to one device
	 */
	Result sendInstantMessage(Map data, String registrationId,
			String apiKey = apiKeyFromConfig()) {
		sendMessage(data: data, registrationIds: [registrationId], apiKey: apiKey)
	}

	/**
	 * Sends an instant message to multiple devices
	 */
	MulticastResult sendMulticastInstantMessage(Map data, List<String> registrationIds,
			String apiKey = apiKeyFromConfig()) {
		sendMessage(data:data, registrationIds: registrationIds, apiKey: apiKey)
	}

	/**
	 * Sends a message (instant by default, collapse if a collapse key is provided)
	 * to one or multiple devices, using the given api key (or obtaining it from the config
	 * if none is provided)
	 * @return a Result (if only one registration id is provided) or a MulticastResult
	 */
	def sendMessage(Map data, List<String> registrationIds, String collapseKey = '',
			String apiKey = apiKeyFromConfig()) {
		sender(apiKey).send(buildMessage(data, collapseKey),
			registrationIds.size() > 1 ? registrationIds : registrationIds[0], retries())
	}

	private Message buildMessage(Map data, String collapseKey = '') {
		withMessageBuilder(data) { messageBuilder ->
			if (collapseKey) {
				messageBuilder.collapseKey(collapseKey).timeToLive(timeToLive())
			}
		}
	}

	private Message withMessageBuilder(Map messageData, Closure builderConfigurator = null) {
		Message.Builder messageBuilder = new Message.Builder().delayWhileIdle(delayWhileIdle())
		if (builderConfigurator) {
			builderConfigurator(messageBuilder)
		}
		addData(messageData, messageBuilder).build()
	}

	private addData(Map data, Message.Builder messageBuilder) {
		data.each {
			messageBuilder.addData(it.key, it.value)
		}
		return messageBuilder
	}

	private sender(apiKey) {
		new Sender(apiKey)
	}

	private apiKeyFromConfig() {
		def key = grailsApplication.config.android.gcm.api.key
		if (!key) {
			throw new ApiKeyNotFoundException(grailsApplication.config.api.key.config.property.name)
		}

		key
	}

	private timeToLive() {
		grailsApplication.config.android.gcm.time.to.live ?: 2419200
	}

	private delayWhileIdle() {
		grailsApplication.config.android.gcm.delay.'while'.idle ?: false
	}

	private retries() {
		grailsApplication.config.android.gcm.retries ?: 1
	}
}
