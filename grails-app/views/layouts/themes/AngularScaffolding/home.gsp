<!DOCTYPE html>
<!--[if lt IE 7]> <html xmlns:ng="http://angularjs.org" class="no-js lt-ie9 lt-ie8 lt-ie7" lang="en"> <![endif]-->
<!--[if IE 7]>    <html xmlns:ng="http://angularjs.org" class="no-js lt-ie9 lt-ie8" lang="en"> <![endif]-->
<!--[if IE 8]>    <html xmlns:ng="http://angularjs.org" class="no-js lt-ie9" lang="en"> <![endif]-->
<!--[if gt IE 8]><!--> <html class="no-js" lang="en"> <!--<![endif]-->
	<theme:head>
		<!--[if lte IE 7]>
			<g:javascript src="json3.min.js" />
		<![endif]-->
		<!--[if lte IE 8]>
	      <script>
	        document.createElement('ng-include');
	        document.createElement('ng-pluralize');
	        document.createElement('ng-view');

	        // Optionally these for CSS
	        document.createElement('ng:include');
	        document.createElement('ng:pluralize');
	        document.createElement('ng:view');
	      </script>
	    <![endif]-->
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		
		<meta name="viewport" content="width=device-width">
		<link rel="shortcut icon" href="${resource(dir: 'images', file: 'favicon.ico')}" type="image/x-icon">
		<link rel="apple-touch-icon" href="${resource(dir: 'images', file: 'apple-touch-icon.png')}">
		<link rel="apple-touch-icon" sizes="114x114" href="${resource(dir: 'images', file: 'apple-touch-icon-retina.png')}">

        <r:layoutResources />
	</theme:head>
	<theme:body bodyAttrs="['data-ng-app']">
		<div id="grailsLogo" role="banner"><a href="http://grails.org"><img src="${resource(dir: 'images', file: 'grails_logo.png')}" alt="Grails"/></a></div>
	    <div class="container">
	        <div class="content">
	            <div class="page-header">
	                <theme:layoutTitle/>
	            </div>
	            <div class="row">
	                <div class="span11">
	                    <theme:layoutZone name="primary-navigation"/>
	                </div>
	            </div>
				<div class="row">
	                <div class="span11">
	                    <theme:layoutZone name="body"/>
	                </div>
	            </div>
				<div class="row">
	                <div class="span11">
	                    <div class="footer" role="contentinfo"></div>
	                </div>
	            </div>
	        </div>
	    </div>
        <r:layoutResources />
	</theme:body>
</html>