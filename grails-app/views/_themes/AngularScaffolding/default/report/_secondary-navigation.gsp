<ui:block type="well">
	<ui:button kind="anchor" data-ng-href="#list">${message(code: 'default.list.label', default: 'List '+entityName, args: [entityName] )}</ui:button>
	
    <ui:button kind="anchor" mode="primary" data-ng-href="#create">${message(code: 'default.new.label', default: 'New '+entityName, args: [entityName] )}</ui:button>
</ui:block>