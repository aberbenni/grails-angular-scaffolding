<% import grails.persistence.Event %>

<g:set var="entityName" value="\${message(code: '${domainClass.propertyName}.label', default: '${className}')}" scope="request"/>
<theme:layout name="report"/>
<theme:title text="${className} list"/>

<theme:zone name="body">
	<gas-alert level="{{message.level}}" text="{{message.text}}"></gas-alert>
	
    <ui:table>
		<thead>
        <ui:tr>
            <% excludedProps = Event.allEvents.toList() << 'id' << 'version'
            allowedNames = domainClass.persistentProperties*.name << 'dateCreated' << 'lastUpdated'
			only = []//grailsApplication.getArtefactByLogicalPropertyName("Controller", controllerName).getPropertyValue("listProperties")
            props = domainClass.properties.findAll { allowedNames.contains(it.name) && !excludedProps.contains(it.name) && it.type != null && !Collection.isAssignableFrom(it.type) } //&& !it.isAssociation() && !it.embedded }
			Collections.sort(props, comparator.constructors[0].newInstance([domainClass] as Object[]))
			if(only)
				props = props.collect{ only.contains(it.name) }
			else if (props.size() > 6)
				props = props[0..5]
			
			props.eachWithIndex { p, i ->
                if (p.isAssociation()) { %>
        			<ui:th><g:message code="${domainClass.propertyName}.${p.name}.label" default="${p.naturalName}"/></ui:th>
        		<% } else { %>
        			<th data-gas-sortable="${p.name}">\${message(code: '${domainClass.propertyName}.${p.name}.label', default: '${p.naturalName}')}</th> 
        		<% }
            } %>
        </ui:tr>
		</thead>
	    <tbody>
	        <tr data-ng-repeat="item in list" data-ng-click="show(item)">
	            <%  for (p in props) { %>
	            <td>{{item.${p.name}}}</td>
	            <%  } %>
	        </tr>
	    </tbody>
        </ui:table>
		
</theme:zone>