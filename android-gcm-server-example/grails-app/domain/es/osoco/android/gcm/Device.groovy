package es.osoco.android.gcm

class Device {

	String token
	
    static constraints = {
		token(nullable:false, blank:false, unique:true)
    }
}
