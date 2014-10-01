This is a Grails plugin that allows you to use [Angular.js](http://angularjs.org/), [Twitter Bootstrap](http://www.getbootstrap.com), [Grails Platform UI Theme](http://platform-ui.org/doc/latest/guide/themes.html) based scaffolding.
This work is derived from original [Grails Angular Scaffolding Plugin](https://github.com/robfletcher/grails-angular-scaffolding) by [Rob Fletcher](https://github.com/robfletcher).

## Usage

After installing the plugin run:

	grails ng-install-templates

This will install the Angular JS scaffolding templates into your project under `src/templates/scaffolding`. It will also copy some common HTML template files that will be shared by all scaffolded views into `web-app/ng-templates`.

### Static scaffolding

To generate the controller and views for a domain class run:

	grails ng-generate-all _domain class name_

### Dynamic scaffolding

Dynamic scaffolding is supported for the controller and views. You will not need to generate the controller and views for each domain class.

## How it works

Instead of the Grails controller rendering a view for each page using a GSP the controller's _index_ action serves up an initial framework page containing the JavaScript resources required by Angular JS. The remaining controller actions simply return _JSON_ data.

Each _'page'_ in the CRUD interface for a particular domain class is accessed using a URL fragment; `#/list`, `#/create`, etc. The page content is rendered by Angular JS using an HTML template and the data to populate the page is retrieved from the controller using an _AJAX_ call.

The HTML templates need to be generated individually for each domain class as they contain the markup needed to represent the properties of that class in a list or a form. However, the JavaScript used for the CRUD interface is the same for all domain classes.

## Customizing

### Enable optimistic locking check

By default, the JSON converter does not send the object's "version" field generated by Hibernate. Because of that, the controller does not check when concurrent modifications occur.
To enable optimistic locking check, simply add the following in your Config.groovy:

grails.converters.json.domain.include.version = true


### Using Grails RESTful URL mappings

By default Grails uses a non-RESTful URL scheme where the controller action representing the verb is part of the URL. In the Grails documentation there is a section on [configuring RESTful URL mappings](http://grails.org/doc/latest/guide/theWebLayer.html#mappingHTTP). If you want to use such a URL scheme with this plugin you will need to override the `web-app/js/grails-default.js` file that configures an Angular _$resource_ service that maps to your Grails controllers.

## Grails Plugins

This project makes use of these plugins:

* [Grails AngularJS Resources Plugin](https://github.com/smartiniOnGitHub/grails-angularjs-resources)
* [Grails Platform Core Plugin](https://github.com/grails-plugins/grails-platform-core)
* [Grails Platform UI Plugin](https://github.com/MerryCoders/grails-platform-ui)
* [Grails plugin for Twitter Bootstrap CSS framework resources](https://github.com/groovydev/twitter-bootstrap-grails-plugin)
* [Fields Plugin](https://github.com/grails-fields-plugin/grails-fields)
* [Grails Scaffolding Plugin](https://github.com/grails-plugins/grails-scaffolding)

## Limitations

This is an experimental work-in-progress. See the [issues list](https://github.com/aberbenni/grails-angular-scaffolding/issues) for outstanding features.

