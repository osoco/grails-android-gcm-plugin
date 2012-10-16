package es.osoco.android.gcm.exception

class ApiKeyNotFoundException extends Exception {

    ApiKeyNotFoundException(String apiKeyPropertyName) {
        super("ApiKeyNotFoundException: Api Key should be in the Config.groovy file as $apiKeyPropertyName.")
    }
}
