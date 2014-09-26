import org.codehaus.groovy.grails.commons.GrailsApplication
import org.codehaus.groovy.grails.commons.GrailsControllerClass
import org.codehaus.groovy.grails.commons.GrailsDomainClass
import grails.plugin.angularscaffolding.AngularTemplateGenerator 
import org.codehaus.groovy.grails.scaffolding.GrailsTemplateGenerator
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationContext
import grails.util.BuildSettingsHolder

import grails.plugin.angularscaffolding.marshalling.AngularObjectMarshallers
import grails.plugin.angularscaffolding.marshalling.AngularDateMarshaller

class AngularScaffoldingGrailsPlugin {

	private Logger log = LoggerFactory.getLogger(getClass())
	
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
		
        platformUi {
			
			/*
            ui.GrailsAngular.actions.cssClass = 'form-actions'
            ui.GrailsAngular.button.cssClass = 'btn'
            ui.GrailsAngular.tab.cssClass = 'tab-pane'
            ui.GrailsAngular.tabs.cssClass = 'nav nav-tabs'
            ui.GrailsAngular.field.cssClass = 'input'
            ui.GrailsAngular.input.cssClass = 'input-xlarge'
            ui.GrailsAngular.invalid.cssClass = 'invalid'
            ui.GrailsAngular.table.cssClass = 'table table-striped'
            ui.GrailsAngular.tr.cssClass = ''
            ui.GrailsAngular.trOdd.cssClass = ''
            ui.GrailsAngular.trEven.cssClass = ''
            ui.GrailsAngular.carousel.cssClass = 'carousel slide'
            ui.GrailsAngular.slide.cssClass = 'item'
            ui.GrailsAngular.form.cssClass = 'form-horizontal'
            ui.GrailsAngular.primaryNavigation.cssClass = 'nav'
            ui.GrailsAngular.secondaryNavigation.cssClass = 'nav nav-pills'
            ui.GrailsAngular.navigation.cssClass = 'nav'
        
		    themes.GrailsAngular.ui.set = "GrailsAngular"*/
        }
		
		application {
			
			grails.databinding.dateFormats = config.grails.databinding.dateFormats + ["yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss.S", "yyyy-MM-dd'T'hh:mm:ss'Z'", "yyyy-MM-dd'T'hh:mm:ss.SSS'Z'"]
			
		}
        
	}
	
	def doWithSpring = {
		
		scaffoldingTemplateGenerator(AngularTemplateGenerator, ref("classLoader")) {
			grailsApplication = ref("grailsApplication")
		}
		
		angularObjectMarshallers( AngularObjectMarshallers ) {
	        marshallers = [
	                new AngularDateMarshaller()
	        ]
	    }
		
	}
	
	def doWithApplicationContext = { ctx ->
		// Custom marshalling
		ctx.getBean( "angularObjectMarshallers" ).register()
		
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
		
		println "onChange"
		
		if (event.source && application.isControllerClass(event.source)) {
			GrailsControllerClass controllerClass = application.getControllerClass(event.source.name)
			doScaffoldingController(event.ctx, event.application, controllerClass)
		}
		else {
			doScaffolding(event.ctx, event.application)
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
