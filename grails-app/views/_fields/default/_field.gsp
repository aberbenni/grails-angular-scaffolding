<div class="control-group" data-ng-class="{error: errors.${prefix}${property}}">
	<label class="control-label" for="${prefix}${property}">${label}</label>
	<div class="controls">
        ${raw(widget)}
		<span class="help-inline" data-ng-show="errors.${prefix}${property}">{{errors.${prefix}${property}}}</span>
	</div>
</div>