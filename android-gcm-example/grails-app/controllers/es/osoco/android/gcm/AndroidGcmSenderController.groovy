package es.osoco.android.gcm

import java.util.List;
import java.util.Map;

import com.google.android.gcm.server.Message
import com.google.android.gcm.server.Result
import com.google.android.gcm.server.Sender

class AndroidGcmSenderController {

    def androidGcmService
	
	def index = { 
		//println params
		Device.findByToken('fakeToken') ?: new Device(token:'fakeToken').save(failOnError:true)
		params.tokens = Device.findAll()*.token
		render view: 'index', model: params
    }
	
	def sendMessage = {
		def deviceTokens = [params.deviceToken].flatten()
		def messages = params.messageKey.inject([:]) {
			currentMessages, currentKey ->
			currentMessages << [ (currentKey) : params.messageValue[currentMessages.size()]]
		}
		boolean multicast = params.multicast 
		println "Sending ${multicast ? 'multicast ' : ''}message $messages to devices: $deviceTokens"
		params.sendResponse = androidGcmService.sendCollapseMessage(
			params.collapseKey, messages, deviceTokens[0])
		println "Obtained response ${params.sendResponse}"
		redirect(action:'index', params: params)
	}
}
