<%=packageName%>
<% import grails.persistence.Event %>

<g:set var="${className.toLowerCase()}Bean" bean="\${${className}.class}"/>
<g:set var="entityName" value="\${message(code: '${domainClass.propertyName}.label', default: '${className}')}" scope="request"/>

<theme:layout name="dataentry"/>
<theme:title text="Edit ${className}"/>

<theme:zone name="body">

    <gas-alert level="{{message.level}}" text="{{message.text}}"></gas-alert>
	
    <ui:form name="form" data-ng-submit="save(item)" class="form-horizontal">
		<input type="text" data-ng-model="item.id">
		<input type="text" data-ng-model="item.version">
        <!--
		<g:textField name="id" data-ng-model="item.id"/>
        <g:hiddenField name="version" data-ng-model="item.version"/>
			-->
        <ui:fieldGroup>
            <g:render template="./${className.toLowerCase()}/form"/>
        </ui:fieldGroup>

        <ui:actions>
			
			<ui:button kind="anchor" data-ng-href="#list"
					   text="\${message(code: 'default.button.cancel.label', default: 'Cancel')}"
					   textArgs="[entityName]"/>
		
            <ui:button type="submit" kind="button" mode="primary"
                       text="\${message(code: 'default.button.update.label', default: 'Update')}"
					   data-ng-disabled="form.\\\$invalid"/>
					   
            <ui:button type="button" kind="button" mode="danger"
                       text="\${message(code: 'default.button.delete.label', default: 'Delete')}"
                       formnovalidate="" value="delete"
                       data-ng-click="delete(item)"/>
			
        </ui:actions>
    </ui:form>
</theme:zone>