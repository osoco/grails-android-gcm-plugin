class AndroidGcmGrailsPlugin {
    // the plugin version
    def version = "0.1"
    // the version or versions of Grails the plugin is designed for
    def grailsVersion = "1.3.7 > *"
    // the other plugins this plugin depends on
    def dependsOn = [:]
    // resources that are excluded from plugin packaging
    def pluginExcludes = [
            "grails-app/views/error.gsp"
    ]

    def author = "OSOCO"
    def authorEmail = "info@osoco.es"
    def organization = [name: "OSOCO", url: "http://www.osoco.es/"]
    def developers = [ 
        [ name: "Adrian Santalla", email: "adrian.santalla@gmail.com" ],
        [ name: "Diego Toharia", email: "diego@toharia.com" ]]
    def title = "Android GCM (Google Cloud Messaging) service plugin"
    def description = '''\\
The plugin provides a service to easily access the Google Cloud Messaging (GCM) services 
'''

    // URL to the plugin's documentation
    def documentation = "http://grails.org/plugin/android-gcm"

    def doWithWebDescriptor = { xml ->
        // TODO Implement additions to web.xml (optional), this event occurs before 
    }

    def doWithSpring = {
        // TODO Implement runtime spring config (optional)
    }

    def doWithDynamicMethods = { ctx ->
        // TODO Implement registering dynamic methods to classes (optional)
    }

    def doWithApplicationContext = { applicationContext ->
        // TODO Implement post initialization spring config (optional)
    }

    def onChange = { event ->
        // TODO Implement code that is executed when any artefact that this plugin is
        // watching is modified and reloaded. The event contains: event.source,
        // event.application, event.manager, event.ctx, and event.plugin.
    }

    def onConfigChange = { event ->
        // TODO Implement code that is executed when the project configuration changes.
        // The event is the same as for 'onChange'.
    }
}
