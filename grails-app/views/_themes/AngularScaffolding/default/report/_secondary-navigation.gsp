
    <ui:block>
		<ui:button kind="button" onclick="window.location='#list';" text="default.list.label" textArgs="[entityName]"/>
		
        <ui:button kind="button" mode="primary" onclick="window.location='#create';"
                   value="Redirect" text="${message(code: 'default.new.label', default: 'New '+entityName, args: [entityName] )}"/>
    </ui:block>