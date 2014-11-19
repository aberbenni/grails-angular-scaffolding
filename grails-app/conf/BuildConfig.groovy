grails.project.work.dir = 'target'
grails.project.class.dir = 'target/classes'
grails.project.test.class.dir = 'target/test-classes'
grails.project.test.reports.dir = 'target/test-reports'
grails.project.target.level = 1.6

grails.project.dependency.resolution = {

    inherits 'global'
    log 'warn'

    repositories {
        grailsCentral()
        mavenCentral()
        mavenLocal()
    }

    dependencies {
    }

	plugins {
		build(":release:2.2.0") {
			export = false
		}
		compile ":scaffolding:2.0.3"
		
		compile ":fields:1.4"
				
		compile(":platform-ui:1.0.RC7"){
			excludes "resources"
		}
		
		compile(":platform-core:1.0.0") {
			excludes "spring-test"
		}
		
		runtime ":resources:1.2.8"
		
		runtime ":angularjs-resources:1.2.26"
		
		runtime ":twitter-bootstrap:3.3.1"
		
	}
	
}
