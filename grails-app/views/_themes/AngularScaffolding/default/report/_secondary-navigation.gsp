
    <ui:block class=" well">
		<ui:button kind="anchor" data-ng-href="#list" text="${message(code: 'default.list.label', default: 'List '+entityName, args: [entityName] )}"/>
		
        <ui:button kind="anchor" mode="primary" data-ng-href="#create"
                   value="Redirect" text="${message(code: 'default.new.label', default: 'New '+entityName, args: [entityName] )}"/>
    </ui:block>