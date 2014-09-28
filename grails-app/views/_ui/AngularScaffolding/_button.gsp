<g:set var="modeClass" value="${mode?'btn-'+mode:'btn-default'}"/>
<g:set var="disabledClass" value="${disabled ? 'disabled' : ''}"/>
<p:callTag tag="p:button" class="${p.joinClasses(values:[buttonClass,classes,modeClass,disabledClass])}" 
    kind="${kind}" attrs="${attrs}" bodyContent="${text}"/>
