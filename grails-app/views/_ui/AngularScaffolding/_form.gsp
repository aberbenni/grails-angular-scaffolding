<form${ui.attributes(attrs:attrs)} class="${hlp.joinClasses(values:[formClass,classes])}">
	${actionsContent}
	<ui:block type="panel">
			${bodyContent}
	</ui:block>
	${actionsContent}
</form>