<%=packageName%>
<% import grails.persistence.Event %>
<g:set var="${className.toLowerCase()}Bean" bean="\${${className}.class}"/>

<%  excludedProps = Event.allEvents.toList() << 'version' << 'dateCreated' << 'lastUpdated'
	persistentPropNames = domainClass.persistentProperties*.name
        boolean hasHibernate = pluginManager?.hasGrailsPlugin('hibernate') || pluginManager?.hasGrailsPlugin('hibernate4') 
        if (hasHibernate) { 
                def GrailsDomainBinder = getClass().classLoader.loadClass('org.codehaus.groovy.grails.orm.hibernate.cfg.GrailsDomainBinder') 
                if (GrailsDomainBinder.newInstance().getMapping(domainClass)?.identity?.generator == 'assigned') { 
                        persistentPropNames << domainClass.identifier.name 
                } 
        } 
	props = domainClass.properties.findAll { persistentPropNames.contains(it.name) && !excludedProps.contains(it.name) }
	Collections.sort(props, comparator.constructors[0].newInstance([domainClass] as Object[]))
	for (p in props) {
		if (p.embedded) {
			def embeddedPropNames = p.component.persistentProperties*.name
			def embeddedProps = p.component.properties.findAll { embeddedPropNames.contains(it.name) && !excludedProps.contains(it.name) }
			Collections.sort(embeddedProps, comparator.constructors[0].newInstance([p.component] as Object[]))
			%><fieldset class="embedded"><legend>${p.naturalName}</legend><%
			for (ep in p.component.properties) {
                if (!(ep.name in excludedProps)) renderFieldForProperty(ep, p.component, "${p.name}.")
			}
			%></fieldset><%
		} else {
			renderFieldForProperty(p, domainClass)
		}
	}

	private renderFieldForProperty(p, owningClass, prefix = "") {
		boolean hasHibernate = pluginManager?.hasGrailsPlugin('hibernate')
		boolean display = true
		boolean required = false
		if (hasHibernate) {
			cp = owningClass.constrainedProperties[p.name]
			display = (cp ? cp.display : true)
			required = (cp ? !(cp.propertyType in [boolean, Boolean]) && !cp.nullable && (cp.propertyType != String || !cp.blank) : false)
		}
		if (display) { %>
			<f:field bean="${className.toLowerCase()}Bean" property="${p.name}"/>
			
	<%  }   } %>
