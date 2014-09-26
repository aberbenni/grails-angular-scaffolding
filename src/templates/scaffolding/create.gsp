<%=packageName%>
<% import grails.persistence.Event %>

<g:set var="${className.toLowerCase()}Bean" bean="\${${className}.class}"/>
<g:set var="entityName" value="\${message(code: '${domainClass.propertyName}.label', default: '${className}')}" scope="request"/>

<theme:layout name="dataentry"/>
<theme:title text="Create ${className}"/>

<theme:zone name="body">

    <gas-alert level="{{message.level}}" text="{{message.text}}"></gas-alert>
	
    <ui:form action="save" data-ng-submit="save(item)" class="form-horizontal">

        <ui:fieldGroup>
            <g:render template="./${className.toLowerCase()}/form"/>
        </ui:fieldGroup>

        <ui:actions>
			
			<ui:button kind="button" onclick="window.location='#list';" 
					   text="\${message(code: 'default.button.cancel.label', default: 'Cancel')}"
					   textArgs="[entityName]"/>
		
            <ui:button type="submit" kind="button" mode="primary" name="create"
                       text="\${message(code: 'default.button.create.label', default: 'Create')}"
					   data-ng-disabled="form.\\\$invalid"/>
        </ui:actions>
    </ui:form>
</theme:zone>

	
<!-- div class="page-header">
	<h1>Create ${className}</h1>
</div>

<form name="form" data-ng-submit="save(item)" class="form-horizontal">
 	<g:render template="./${className.toLowerCase()}/form"/>
	<div class="form-actions">
		<button type="submit" class="btn btn-primary" data-ng-disabled="form.\$invalid"><i class="icon-ok"></i> Create</button>
	</div>
</form -->




<%
/*
	private renderFieldForProperty(p, owningClass, prefix = "") {
	...
	<div class="control-group" data-ng-class="{error: errors.${prefix}${p.name}}">
		<label class="control-label" for="${prefix}${p.name}">${p.naturalName}</label>
		<div class="controls">
            ${renderEditor(p, prefix)}
			<span class="help-inline" data-ng-show="errors.${prefix}${p.name}">{{errors.${prefix}${p.name}}}</span>
		</div>
	</div>
	
*/
%>
