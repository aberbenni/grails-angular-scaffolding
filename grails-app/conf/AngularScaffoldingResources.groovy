modules = {
	'angular-scaffolding' {
        dependsOn 'angular-resource', 'angular-route'
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