/**
 * Creates a new Grails dynamic scaffolding controller for given domain classes.
 *
 * @author Graeme Rocher
 *
 */
includeTargets << grailsScript("_GrailsBootstrap")
includeTargets << grailsScript("_GrailsCreateArtifacts")

target (ngCreateScaffoldController: "Creates new scaffolding controller for a specified domain class") {
	depends(checkVersion, packageApp, loadApp)
	
	println argsMap["params"]
	
	( pkg, name ) = parseArgs()

	println "$pkg $name"

	String suffix = "Controller"
	
	def names = []
	
	if (!name || name == '*') {
		names = uberCreate()
	} else {
		names = argsMap["params"][1..-1]
	}
	
	println names

	for (name in names) {
		name = purgeRedundantArtifactSuffix(name, suffix)

		// Does the corresponding domain class exist?
		def dcFile = new File("${basedir}/grails-app/domain", name.replace('.' as char, '/' as char) + ".groovy")
		if (!dcFile.exists()) {
			grailsConsole.error "No domain class found for '$name'"
			return
		}

		def replacements = ["@domain.class@":name]

		name = pkg+"."+name[(name.lastIndexOf('.') + 1)..-1] //name = [package].[domain class name without package]

		println "$name $suffix $replacements"

		createArtifact(
			name: name,
			suffix: suffix,
			type: "ScaffoldingController",
			path: "grails-app/controllers",
			skipPackagePrompt: true,
			replacements:replacements)

		String viewsDir = "${basedir}/grails-app/views/${propertyName}"
		ant.mkdir(dir:viewsDir)
		event("CreatedFile", [viewsDir])

		createUnitTest(name: name, suffix: suffix, superClass: "ControllerUnitTestCase", skipPackagePrompt: true)
	}
}

private uberCreate() {
	def names = []
	//depends(loadApp)

	def domainClasses = grailsApp.domainClasses

	if (!domainClasses) {
		println "No domain classes found in grails-app/domain, trying hibernate mapped classes..."
		bootstrap()
		domainClasses = grailsApp.domainClasses
	}

	if (!domainClasses) {
		event("StatusFinal", ["No domain classes found"])
		return
	}

	domainClasses.each { domainClass -> names += domainClass.getFullName() }
	names
}

private parseArgs() {
    args = args ? args.split('\n') : []
    switch (args.size()) {
        case 2:
            ant.echo message: "Creating controllers in package ${args[0]}"
            return args
        default:
            ant.echo message: USAGE, level: "error"
            System.exit(1)
            break
    }
}

USAGE = """
Usage: grails ng-create-scaffold-controller [PACKAGE] [NAME]

where
    PACKAGE    = The package of controller(s)
    NAME       = Either a domain class name (case-sensitive) or a wildcard (*). If you specify the wildcard then controllers will be generated for all domain classes.

Examples: 
    grails ng-create-scaffold-controller com.yourapp.controller "*"
    grails ng-create-scaffold-controller com.yourapp.controller com.yourapp.domain.Book
"""

setDefaultTarget 'ngCreateScaffoldController'
