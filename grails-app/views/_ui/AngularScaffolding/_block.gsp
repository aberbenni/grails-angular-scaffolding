<g:if test="${attrs.type == 'panel'}">
	<div class="panel panel-${attrs.mode?:'default'}" ${ui.attributes([excludes:'type'])}>
		<g:if test="${title}">
			<div class="panel-heading">Panel heading without title</div>
		</g:if>
		<div class="panel-body">
			${bodyContent}
		</div>
	</div>
</g:if>
<g:elseif test="${attrs.type} == 'well'">
	<div class="well${classes}" ${ui.attributes([excludes:'type'])}>
		<g:if test="${title}">
		     <ui:h2>${title.encodeAsHTML()}</ui:h2>
	 	</g:if>
	    ${bodyContent}
	</div>
</g:elseif>
<g:else>
	<div class="block${classes}" ${ui.attributes()}>
		<g:if test="${title}">
			<ui:h2>${title.encodeAsHTML()}</ui:h2>
		</g:if>
		${bodyContent}
	</div>
</g:else>