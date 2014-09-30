<%=packageName%>
<% import grails.persistence.Event %>

<g:set var="${className.toLowerCase()}Bean" bean="\${${className}.class}"/>
<g:set var="entityName" value="\${message(code: '${domainClass.propertyName}.label', default: '${className}')}" scope="request"/>

<theme:layout name="dataentry"/>
<theme:title text="Show ${className}"/>

<theme:zone name="body">
	
	<gas-alert level="{{message.level}}" text="{{message.text}}"></gas-alert>
	<ui:block type="panel">
		<dl class="dl-horizontal">
			<%  excludedProps = Event.allEvents.toList() << 'id' << 'version'
			allowedNames = domainClass.persistentProperties*.name << 'dateCreated' << 'lastUpdated'
			props = domainClass.properties.findAll { allowedNames.contains(it.name) && !excludedProps.contains(it.name) }
			Collections.sort(props, comparator.constructors[0].newInstance([domainClass] as Object[]))
			props.each { p -> %>
			<dt>${p.naturalName}</dt>
			<dd data-ng-bind="item.${p.name}"></dd>
			<%  } %>
		</dl>
	</ui:block>
		<!-- actions -->
	<ui:block type="well">
		<ui:button kind="anchor" mode="primary"
				   text="\${message(code: 'default.button.edit.label', default: 'Edit')}"
				   textArgs="[entityName]"
				   data-ng-href="#/edit/{{item.id}}" />

		<ui:button kind="button" mode="danger"
				   text="\${message(code: 'default.button.delete.label', default: 'Delete')}"
				   data-ng-click="delete(item)"/>
	</ui:block>
</theme:zone>