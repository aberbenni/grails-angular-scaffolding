<%=packageName%>
<% import grails.persistence.Event %>
<g:set var="${className.toLowerCase()}Bean" bean="\${${className}.class}"/>

<div class="page-header">
	<h1>Create ${className}</h1>
</div>
<gas-alert level="{{message.level}}" text="{{message.text}}"></gas-alert>
<form name="form" data-ng-submit="save(item)" class="form-horizontal">
 	<g:render template="./${className.toLowerCase()}/form"/>
	<div class="form-actions">
		<button type="submit" class="btn btn-primary" data-ng-disabled="form.\$invalid"><i class="icon-ok"></i> Create</button>
	</div>
</form>




<%
/*
	
	<div class="control-group" data-ng-class="{error: errors.${prefix}${p.name}}">
		<label class="control-label" for="${prefix}${p.name}">${p.naturalName}</label>
		<div class="controls">
            ${renderEditor(p, prefix)}
			<span class="help-inline" data-ng-show="errors.${prefix}${p.name}">{{errors.${prefix}${p.name}}}</span>
		</div>
	</div>
	
*/
%>
