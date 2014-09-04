import grails.util.GrailsUtil

def fileVersion = GrailsUtil.isDevelopmentEnv() ? '1.2.23' : '1.2.23.min'

modules = {
    angular {
        resource id: 'js', url: [plugin: 'angular-scaffolding', dir: 'js/angular', file: "angular-${fileVersion}.js"], nominify: true, disposition: 'head'
    }

    'angular-resource' {
		dependsOn 'angular'
        resource id: 'js', url: [plugin: 'angular-scaffolding', dir: 'js/angular', file: "angular-resource-${fileVersion}.js"], nominify: true
    }
	
    'angular-route' {
		dependsOn 'angular'
        resource id: 'js', url: [plugin: 'angular-scaffolding', dir: 'js/angular', file: "angular-route-${fileVersion}.js"], nominify: true
    }
	
    'angular-scaffolding' {
        dependsOn 'jquery', 'angular-resource', 'angular-route'
        resource id: 'js', url: [plugin: 'angular-scaffolding', dir: 'js', file: 'scaffolding.js']
        resource id: 'css', url: [plugin: 'angular-scaffolding', dir: 'css', file: 'scaffolding.css']
    }

	'angular-grails-default' {
		dependsOn 'angular-scaffolding'
		resource id: 'js', url: [plugin: 'angular-scaffolding', dir: 'js', file: 'grails-default.js']
	}

	'angular-grails-resource' {
		dependsOn 'angular-scaffolding'
		resource id: 'js', url: [plugin: 'angular-scaffolding', dir: 'js', file: 'grails-resource.js']
	}
}