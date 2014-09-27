<div class="navbar navbar-default navbar-static-top" role="navigation">
		<div class="container">
		  <div class="navbar-header">
		    <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target=".navbar-collapse">
		      <span class="sr-only">Toggle navigation</span>
		      <span class="icon-bar"></span>
		      <span class="icon-bar"></span>
		      <span class="icon-bar"></span>
		    </button>
		    <div id="grailsLogo" role="banner"><a href="http://grails.org"><img style="padding-top:4px" src="${resource(dir: 'images', file: 'grails_logo.png')}" alt="Grails"/></a></div>
		  </div>
		  <div class="navbar-collapse collapse">	 
			<nav:menu id="nav" class="nav navbar-nav" custom="true" scope="app" depth="2" forceChildren="true">
			<li class="${item.data.icon ? 'i_'+item.data.icon+' ' : ''}dropdown">
				<p:callTag tag="g:link"
			                 attrs="${linkArgs + [class:active ? 'active' : ''] + ['data-toggle':'dropdown', class:'dropdown-toggle']}">
			             <nav:title item="${item}"/>
			    </p:callTag>

				<!-- //TODO create scaffolded angular dropdown navigation  -->
			    <g:if test="${item.children}">
				    <nav:menu scope="${item.id}" custom="true" class="dropdown-menu list-inline">
				           <li class="${item.data.icon ? 'i_'+item.data.icon : ''}">
				               <p:callTag tag="g:link"
				                          attrs="${linkArgs + [class:active ? 'active' : '']}">
				                      <nav:title item="${item}"/>
				               </p:callTag>
				    	   </li>
				    </nav:menu>
			    </g:if>
			  </li>
			</nav:menu>
		  
            <!-- ul class="nav navbar-nav navbar-right">
              <li class="active"><a href="./">Default</a></li>
              <li><a href="../navbar-static-top/">Static top</a></li>
              <li><a href="../navbar-fixed-top/">Fixed top</a></li>
            </ul -->
			
          </div><!--/.nav-collapse -->
        </div>
      </div> 