package es.osoco.android.gcm

import java.util.List;
import java.util.Map;

class AndroidGcmSenderController {

    def androidGcmService
	def grailsApplication
	
	def index = { 
		params.tokens = Device.findAll()*.token
		params.apiKey = grailsApplication.config.android.gcm.api.key ?: ''
		render view: 'index', model: params
    }
	
	def sendMessage = {
		['deviceToken', 'messageKey', 'messageValue'].each { 
			key -> params[key] = [params[key]].flatten().findAll { it } 
		}
		def messages = params.messageKey.inject([:]) {
			currentMessages, currentKey ->
			currentMessages << [ (currentKey) : params.messageValue[currentMessages.size()]]
		}
		flash.message = 'received.message.response'
		flash.args = [androidGcmService.sendMessage(messages, params.deviceToken, 
			params.collapseKey, params.apiKey).toString()]
		redirect(action:'index', params: params)
	}
}
