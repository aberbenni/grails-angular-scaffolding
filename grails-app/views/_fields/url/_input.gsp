<%@page defaultCodec="none" %>
<input type="url" id="${property}" name="${property}" data-ng-model="item.${property}" ${required?'required="required"':''}>