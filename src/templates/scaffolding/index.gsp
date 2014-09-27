<% import grails.persistence.Event %>
<%=packageName%>
<!doctype html>
<html>
    <head>
        <g:set var="entityName" value="\${message(code: '${domainClass.propertyName}.label', default: '${className}')}" scope="request"/>
		<theme:title text="\${message(code: '${domainClass.propertyName}.label', default: '${className}')}"/>
		<theme:layout name="home"/>
		
		<r:require module="ui-bootstrap"/>
		<r:require module="angular-scaffolding"/>
        <r:require module="angular-grails-default"/>
		<script>
			angular.module('grailsService', ['ngResource'])
				.constant("baseUrl", "\${createLink(uri: '/${domainClass.propertyName}/')}");
			
			angular.module('scaffolding', ['grailsService', 'flashService', 'ngRoute'])
				.constant("templateUrl", "\${createLink(uri: '/ng-templates/'+controllerName)}")
				.constant("commonTemplateUrl", "\${createLink(uri: '/ng-templates')}");
		</script>
    </head>	
    <body id="ng-app" data-ng-app="scaffolding">
		<theme:zone name="body">
	        <div class="content" role="main" data-ng-view>
			
	        </div>
		</theme:zone>
    </body>
</html>