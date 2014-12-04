eventCompileStart = { kind ->
	
	def angularScaffoldingConfigFile = new File("${basedir}/grails-app/conf/AngularScaffoldingConfig.groovy")
	if (!angularScaffoldingConfigFile.exists()){
	
		event("StatusUpdate", ["copying AngularScaffoldingConfig.groovy"])
		ant.copy(file: "${angularScaffoldingPluginDir}/src/config/AngularScaffoldingConfig.groovy", todir: "${basedir}/grails-app/conf")
		
	}
	
	// Add configuration files to grails.config.locations.
	def newConfigFiles = ["classpath:AngularScaffoldingConfig.groovy"]
	event("StatusUpdate", ["Adding $newConfigFiles configuration files to grails.config.locations"])
	// Get the application's Config.groovy file
	def cfg = new File("${basedir}/grails-app/conf/Config.groovy")
	def cfgText = cfg.text
	def appendedText = new StringWriter()
	
	// Slurp the configuration so we can look at grails.config.locations.
	def config = new ConfigSlurper().parse(cfg.toURL());
	// If it isn't defined, create it as a list.
	if (config.grails.config.locations.getClass() == groovy.util.ConfigObject) {
		appendedText.println ""
		appendedText.println ("// Added by angular-scaffolding plugin")
		appendedText.println('grails.config.locations = []')
	} else {
		// Don't add configuration files that are already on the list.
		newConfigFiles = newConfigFiles.grep {
			!config.grails.config.locations.contains(it)
		};
		
		if(newConfigFiles) {
			appendedText.println ""
			appendedText.println ("// Added by angular-scaffolding plugin")
		}
	}
	// Add each surviving location to the list.
	newConfigFiles.each {
		// The name will have quotes around it...
		appendedText.println "grails.config.locations << \"$it\""
	}
	// Write the new configuration code to the end of Config.groovy.
	cfg.append(appendedText.toString())
}

eventCompileEnd = {
	
	event("StatusUpdate", ["Adding angular-scaffolding configuration files to classpath"]) 
	ant.copy(todir:classesDirPath) {
		fileset(file:"${basedir}/grails-app/conf/AngularScaffoldingConfig.groovy")
	}
}