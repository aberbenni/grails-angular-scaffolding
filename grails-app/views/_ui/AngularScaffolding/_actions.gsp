<%--
  Tag: ui.actions
  Usage: Container for actions of a form. Buttons and links are supplied as the body.

  Available variables:

  classes - String of classes supplied
  actionsClass - Class to apply to the actions container
  bodyContent - The body of the actions container
  attrs - Any attributes to pass through
--%>
<ui:block type="well" class="${hlp.joinClasses(values:[actionsClass, classes])}">
	${bodyContent}
</ui:block>