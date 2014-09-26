<%=packageName%>
<% import grails.persistence.Event %>

<g:set var="${className.toLowerCase()}Bean" bean="\${${className}.class}"/>
<g:set var="entityName" value="\${message(code: '${domainClass.propertyName}.label', default: '${className}')}" scope="request"/>

<theme:layout name="dataentry"/>
<theme:title text="Edit ${className}"/>

<theme:zone name="body">

    <gas-alert level="{{message.level}}" text="{{message.text}}"></gas-alert>
	
    <ui:form action="save" data-ng-submit="save(item)" class="form-horizontal">
		<input type="hidden" data-ng-model="item.id">
		<input type="hidden" data-ng-model="item.version">
        <g:hiddenField name="id" data-ng-model="item.id"/>
        <g:hiddenField name="version" data-ng-model="item.version"/>
        <ui:fieldGroup>
            <g:render template="./${className.toLowerCase()}/form"/>
        </ui:fieldGroup>

        <ui:actions>
			
			<ui:button kind="button" mode="secondary" onclick="window.location='#list';" value="Redirect"
					   text="\${message(code: 'default.button.cancel.label', default: 'Cancel')}"
					   textArgs="[entityName]"/>
		
            <ui:button type="submit" kind="button" mode="primary" name="update"
                       text="\${message(code: 'default.button.update.label', default: 'Update')}"
					   data-ng-disabled="form.\\\$invalid"/>
					   
            <ui:button type="submit" kind="button" mode="danger" name="delete"
                       text="\${message(code: 'default.button.delete.label', default: 'Delete')}"
                       formnovalidate="" value="delete"
                       data-ng-click="delete(item)"/>
			
        </ui:actions>
    </ui:form>
</theme:zone>


<!-- div class="page-header">
	<h1>Edit ${className}</h1>
</div>
<gas-alert level="{{message.level}}" text="{{message.text}}"></gas-alert>
<form name="form" data-ng-submit="update(item)" class="form-horizontal">
	<input type="hidden" data-ng-model="item.id">
	<input type="hidden" data-ng-model="item.version">
 	<g:render template="./${className.toLowerCase()}/form"/>
	<div class="form-actions">
		<button type="submit" class="btn btn-primary" data-ng-disabled="form.\$invalid"><i class="icon-ok"></i> Update</button>
		<button type="button" class="btn btn-danger" data-ng-click="delete(item)"><i class="icon-trash"></i> Delete</button>
	</div>
</form -->
