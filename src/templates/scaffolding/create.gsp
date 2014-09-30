<%=packageName%>
<% import grails.persistence.Event %>

<g:set var="${className.toLowerCase()}Bean" bean="\${${className}.class}"/>
<g:set var="entityName" value="\${message(code: '${domainClass.propertyName}.label', default: '${className}')}" scope="request"/>

<theme:layout name="dataentry"/>
<theme:title text="Create ${className}"/>

<theme:zone name="body">
	<gas-alert level="{{message.level}}" text="{{message.text}}"></gas-alert>
	
	<ui:form name="form" data-ng-submit="save(item)">
		<ui:fieldGroup>
			<g:render template="./${className.toLowerCase()}/form"/>
		</ui:fieldGroup>
		<ui:actions>
			<ui:button kind="anchor" data-ng-href="#list"
				text="\${message(code: 'default.button.cancel.label', default: 'Cancel')}"
				textArgs="[entityName]"/>
			<ui:button kind="submit" mode="primary" name="create"
				text="\${message(code: 'default.button.create.label', default: 'Create')}"
				data-ng-disabled="form.\\\$invalid"/>
		</ui:actions>
	</ui:form>
</theme:zone>