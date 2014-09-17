<%@page defaultCodec="none" %>
<input type="text" id="${property}" name="${property}" data-ng-model="item.${property}" ${required?'required="required"':''}>