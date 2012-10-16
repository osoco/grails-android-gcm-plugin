grails.project.work.dir = 'target'
grails.project.source.level = 1.6

grails.project.dependency.resolution = {

	inherits 'global'
	log 'warn'

	repositories {
		grailsCentral()
		mavenLocal()
		mavenCentral()

		mavenRepo 'https://github.com/slorber/gcm-server-repository/raw/master/releases'
	}

	dependencies {
		runtime 'com.googlecode.json-simple:json-simple:1.1'

		compile 'com.google.android.gcm:gcm-server:1.0.2'
	}

	plugins {
		build(':release:2.0.4', ':rest-client-builder:1.0.2') {
			export = false
		}
	}
}
