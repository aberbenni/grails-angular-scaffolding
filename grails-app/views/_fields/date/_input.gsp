<%@page defaultCodec="none" %>
<input type="date" id="${property}" name="${property}" data-ng-model="item.${property}"
	${required?'required="required"':''}
	${constraints.min!=null?'min="'+constraints.min+'"':''}
	${constraints.max!=null?'max="'+constraints.max+'"':''}
	>
