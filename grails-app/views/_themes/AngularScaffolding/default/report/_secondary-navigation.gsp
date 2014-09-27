
    <ui:block class=" well">
		<ui:button kind="anchor" onclick="window.location='#list';" text="default.list.label" textArgs="[entityName]"/>
		
        <ui:button kind="button" mode="primary" onclick="window.location='#create';"
                   value="Redirect" text="${message(code: 'default.new.label', default: 'New '+entityName, args: [entityName] )}"/>
    </ui:block>