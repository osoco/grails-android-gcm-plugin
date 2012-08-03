package es.osoco.android.gcm

import com.google.android.gcm.server.Message
import com.google.android.gcm.server.Result
import com.google.android.gcm.server.Sender

class AndroidGcmSenderController {

    def androidGcmService
	
	def index = { 
		//println params
		params.tokens = Device.findAll()*.token
		render view: 'index', model: params
    }
	
	def sendMessage = {
		def deviceTokens = [params.deviceToken].flatten()
		def messages = params.messageKey.inject([:]) {
			currentMessages, currentKey ->
			currentMessages << [ (currentKey) : params.messageValue[currentMessages.size()]]
		}
		println "Sending message $messages to devices: $deviceTokens"
		/*params.sendResponse = androidGcmService.sendMessage(
			new Message.Builder()
			.collapseKey(params.collapseKey)
			.timeToLive(3)
			.delayWhileIdle(true)
			.build(),
			params.deviceToken, 1
		)*/
		redirect(action:'index', params: params)
	}
}
