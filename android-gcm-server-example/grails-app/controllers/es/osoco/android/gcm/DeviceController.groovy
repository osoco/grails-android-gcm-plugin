package es.osoco.android.gcm

class DeviceController {

    def subscribe = {
		println "${new Date()}: $params"
		if (params.deviceToken && !Device.findByToken(params.deviceToken)) {
			new Device(token:params.deviceToken).save(failOnError:true)
		}
		render ""
	}
}
