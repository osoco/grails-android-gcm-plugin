grails-android-gcm-plugin
=========================

A plugin that provides a frontend for accesing Google Cloud Messaging service for Android. 
The plugin provides a service (androidGcmService) that has the following methods:
   - sendCollapseMessage - sends a collapse message to one device
   - sendMulticastCollapseMessage - sends a collapse message to multiple devices
   - sendInstantMessage - sends an instant message to one device
   - sendMulticastInstantMessage - sends and instant message to multiple devices
   - sendMessage - sends a message (collapsed if a collapse key is provided) to the given devices

The plugin can be configured with the following options (to be placed in the project Config.groovy):
   - android.gcm.api.key - The GCM service API key. Must be passed as a method parameter if is not provided in the config
   - android.gcm.time.to.live - The message TTL (2419200 by default)
   - android.gcm.delay.'while'.idle - Should the message be delayed with the device is idle (false by default)
   - android.gcm.retries - Number of message sent retries (1 by default)

A sample project that uses the plugin is available in https://github.com/osoco/grails-android-gcm-server-example (the
most interesting class is grails-app/controllers/es/osoco/android/gcm/AndroidGcmSenderController.groovy, which uses the 
Android GCM Service). This sample project is live and can be visited in http://grails-android-gcm-sender.herokuapp.com/
