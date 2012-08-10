import es.osoco.android.gcm.Device
import com.google.android.gcm.server.Result
import com.google.android.gcm.server.MulticastResult

class BootStrap {

    def init = { servletContext ->
		['aFakeDevice', 'anotherFakeDevice'].each {
			deviceToken ->
			Device.findByToken(deviceToken) ?: new Device(token:deviceToken).save(failOnError:true)
		}
		Result.metaClass.toString { ->
			"Result(messageId: $messageId, canonicalRegistrationId: $canonicalRegistrationId, errorCodeName: $errorCodeName)"
		}
		MulticastResult.metaClass.toString { ->
			"MulticastResult(multicastId: $multicastId, retryMulticastIds: $retryMulticastIds, total: $total, success: $success, failure: $failure, canonicalIds: $canonicalIds, results: ${results.collect { it.toString() }})"
		}
    }
    def destroy = {
    }
}
