import org.codehaus.groovy.grails.commons.GrailsApplication
import org.codehaus.groovy.grails.commons.GrailsControllerClass
import org.codehaus.groovy.grails.commons.GrailsDomainClass
import grails.plugin.angularscaffolding.AngularTemplateGenerator
import org.codehaus.groovy.grails.scaffolding.GrailsTemplateGenerator
import org.springframework.context.ApplicationContext
import grails.util.BuildSettingsHolder

import grails.converters.JSON
import grails.rest.render.json.JsonRenderer
import grails.rest.render.json.JsonCollectionRenderer
import org.codehaus.groovy.grails.web.mime.MimeType

import grails.plugin.angularscaffolding.marshalling.DomainClassMarshaller
import grails.plugin.angularscaffolding.marshalling.AngularObjectMarshallers
import grails.plugin.angularscaffolding.marshalling.AngularDateMarshaller

class AngularScaffoldingGrailsPlugin {

    //private Logger log = LoggerFactory.getLogger(getClass())
	
    def version = '1.0-SNAPSHOT'
    def grailsVersion = '2.0 > *'
    def dependsOn = [:]
    def pluginExcludes = []
    def loadBefore = ['platformUi']
    def loadAfter = ['controllers', 'groovyPages','scaffolding']

    def title = 'Angular Scaffolding Plugin'
    def author = 'Rob Fletcher'
    def authorEmail = 'rob@freeside.co'
    def description = '''\
A plugin that enables Grails scaffolding to operate as an Angular.js one-page app.
'''

    def documentation = 'http://freeside.co/grails-angular-scaffolding'
    def license = 'MIT'
    def organization = [name: 'Freeside Software', url: 'http://freeside.co/']
    def issueManagement = [system: 'GitHub', url: 'https://github.com/robfletcher/grails-angular-scaffolding/issues']
    def scm = [url: 'https://github.com/robfletcher/grails-angular-scaffolding']
	
	def observe = ['controllers', 'domainClass']
	
	def doWithConfig = { config ->
		
		log.debug "AngularScaffoldingGrailsPlugin.doWithConfig"
		
		platformUi {
			ui.AngularScaffolding.logo.cssClass = 'navbar-brand'
			ui.AngularScaffolding.actions.cssClass = ''
			ui.AngularScaffolding.button.cssClass = 'btn'
			ui.AngularScaffolding.tab.cssClass = 'tab-pane'
			ui.AngularScaffolding.tabs.cssClass = 'nav nav-tabs'
			ui.AngularScaffolding.field.cssClass = 'input'
			ui.AngularScaffolding.input.cssClass = 'input-xlarge'
			ui.AngularScaffolding.invalid.cssClass = 'invalid'
			ui.AngularScaffolding.table.cssClass = 'table table-striped table-bordered table-hover'
			ui.AngularScaffolding.tr.cssClass = ''
			ui.AngularScaffolding.trOdd.cssClass = ''
			ui.AngularScaffolding.trEven.cssClass = ''
			ui.AngularScaffolding.carousel.cssClass = 'carousel slide'
			ui.AngularScaffolding.slide.cssClass = 'item'
			ui.AngularScaffolding.form.cssClass = 'form-horizontal'
			ui.AngularScaffolding.primaryNavigation.cssClass = 'nav'
			ui.AngularScaffolding.secondaryNavigation.cssClass = 'nav nav-pills'
			ui.AngularScaffolding.navigation.cssClass = 'nav'
			
			themes.AngularScaffolding.ui.set = "AngularScaffolding"
		}
		
		application {
			if(config.grails.databinding.dateFormats){
				grails.databinding.dateFormats = config.grails.databinding.dateFormats + ["yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss.S", "yyyy-MM-dd'T'hh:mm:ss'Z'", "yyyy-MM-dd'T'hh:mm:ss.SSS'Z'"]
			} else {
				grails.databinding.dateFormats = ["yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss.S", "yyyy-MM-dd'T'hh:mm:ss'Z'", "yyyy-MM-dd'T'hh:mm:ss.SSS'Z'"]
				grails.databinding.dateFormats.unique()
			} 
			grails.databinding.dateFormats.unique()
			
			
		}
	}
	
	def doWithSpring = {
		
		log.debug "AngularScaffoldingGrailsPlugin.doWithSpring"
		
		scaffoldingTemplateGenerator(AngularTemplateGenerator, ref("classLoader")) {
			grailsApplication = ref("grailsApplication")
		}
		
		angularObjectMarshallers( AngularObjectMarshallers ) {
			marshallers = [
				new AngularDateMarshaller()
			]
		}
		
		//default excludes class field
		def excludesList = ['class']
		if(application.config.angularScaffolding.render.excludesList) {
			excludesList = application.config.angularScaffolding.render.excludesList
		}
		
		// for all domain classes in the application.
		for (domainClass in application.domainClasses) {
			
			// Register JSON renderer for domainClass resource with short output.
			"json${domainClass.shortName}ShortRenderer"(JsonRenderer, domainClass.clazz) {
				// Grails will compare the name of the MimeType
				// to determine which renderer to use. So we 
				// use our own custom name here. 
				// The second argument, 'short', specifies the
				// supported extension. We can now use
				// the request parameter format=short to use
				// this renderer for the domainClass resource.
				mimeTypes = [new MimeType('application/angular-scaffolding.short+json', 'short')]

				// Here we specify the named configuration
				// that must be used by an instance
				// of this renderer.
				namedConfiguration = 'short'
			}
			
			"json${domainClass.shortName}ShortCollectionRenderer"(JsonCollectionRenderer, domainClass.clazz) {
				mimeTypes = [new MimeType('application/angular-scaffolding.short+json', 'short')]
				namedConfiguration = 'short'
			}
			
			// Default JSON renderer as fallback.
			"json${domainClass.shortName}Renderer"(JsonRenderer, domainClass.clazz) {
				excludes = excludesList
			}
			"json${domainClass.shortName}CollectionRenderer"(JsonCollectionRenderer, domainClass.clazz) {
				excludes = excludesList
			}		
			
		}
		
	}
	
	def doWithApplicationContext = { ctx ->
		
		log.debug "AngularScaffoldingGrailsPlugin.doWithApplicationContext"
		
		// Custom marshalling
		ctx.getBean( "angularObjectMarshallers" ).register()
		
		JSON.createNamedConfig('short') { nc ->
			for (domainClass in application.domainClasses) {
				Closure c = DomainClassMarshaller.createIncludeMarshaller(domainClass.clazz, ['id','toString'])
				nc.registerObjectMarshaller(domainClass.clazz, c)
			}
		}
		
		def config = application.config
		
		config.grails.mime.types['short'] = ['application/angular-scaffolding.short+json', 'application/json']
		
		//TODO if config.grails.plugin.angularscaffolding.dynamicScaffolding == true 
		if (application.warDeployed) {
			doScaffolding ctx, application
			return
		}
		
		try {
			log.info "do scaffolding..."
			doScaffolding ctx, application
			log.info "done scaffolding."
		}
		catch (e) {
			log.error "Error doing scaffolding: $e.message", e
		}
		
	}

	def onChange = { event ->
		
		log.debug "AngularScaffoldingGrailsPlugin.onChange"
		
		def dynamicScaffold = false //TODO make configurable
		
		if(dynamicScaffold){
			if (event.source && application.isControllerClass(event.source)) {
				GrailsControllerClass controllerClass = application.getControllerClass(event.source.name)
				doScaffoldingController(event.ctx, event.application, controllerClass)
			} else {	
					doScaffolding(event.ctx, event.application)
			}
		}
	}
	
	private void doScaffolding(ApplicationContext ctx, GrailsApplication application) {
		for (controllerClass in application.controllerClasses) {
			doScaffoldingController(ctx, application, controllerClass)
		}
	}

	private void doScaffoldingController(ApplicationContext ctx, GrailsApplication application, GrailsControllerClass controllerClass) {

		def scaffoldProperty = controllerClass.getPropertyValue("scaffold", Object)
		if (!scaffoldProperty || !ctx) {
			return
		}
		
		GrailsDomainClass domainClass = getScaffoldedDomainClass(application, controllerClass, scaffoldProperty)
		
		if (!domainClass) {
			log.error "Cannot generate controller logic for scaffolded class {}. It is not a domain class!", scaffoldProperty
			return
		}
		
		GrailsTemplateGenerator generator = ctx.scaffoldingTemplateGenerator
		
		ClassLoader parentLoader = ctx.classLoader
		
		def basedir = BuildSettingsHolder.settings.baseDir.toString()
		generator.generateViews(domainClass, basedir)     
	}

	private GrailsDomainClass getScaffoldedDomainClass(application, GrailsControllerClass controllerClass, scaffoldProperty) {

		if (!scaffoldProperty) {
			return null
		}

		if (scaffoldProperty instanceof Class) {
			return application.getDomainClass(scaffoldProperty.name)
		}

		scaffoldProperty = controllerClass.packageName ? "${controllerClass.packageName}.${controllerClass.name}" : controllerClass.name
		return application.getDomainClass(scaffoldProperty)
	}
	
}
