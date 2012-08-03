package es.osoco.android.gcm

class AndroidGcmService {

    static transactional = true
	def grailsApplication 

    private getApiKey() {
		grailsApplication.config.android.gcm.api.key
    }
}
