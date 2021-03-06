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
		<!-- navbar -->
		<theme:layoutZone name="primary-navigation"/>
		<!-- content -->
	    <div class="container">
			<div class="content">
				<!-- ng-view -->
				<theme:layoutZone name="body"/>
				<!-- /ng-view -->
	        </div>
	    </div>
		<!-- footer -->
		<div class="footer">
		      <div class="container">
		        <theme:layoutZone name="footer"/>
		      </div>
		</div>
        <r:layoutResources />
	</theme:body>
</html>