<%@page defaultCodec="none" %>
<g:if test="${constraints.range}">
	<input type="range" id="${property}" name="${property}" data-ng-model="item.${property}"
		${required?'required="required"':''}
		min="${constraints.range?.from?:0}"
		max="${constraints.range?.to?:3}"	
		>
		<output for="${property}">{{item.${property}}}</output>
</g:if>
<g:else>
	<input type="number" id="${property}" name="${property}" data-ng-model="item.${property}"
		${required?'required="required"':''}
		${constraints.min!= null?'min="'+constraints.min+'"':''}
		${constraints.max!= null?'max="'+constraints.max+'"':''}	
		>
</g:else>
