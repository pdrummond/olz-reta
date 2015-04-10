"use strict";

var $ = require('jquery');
var Backbone = require('backbone');
Backbone.$ = $;

var AppView = require("./AppView");

module.exports = Backbone.Router.extend({

	routes: {		
		'*query'	: 'showHomeView',		
	},

	initialize: function() {		
	},
	
	showHomeView: function() {
		var AppView = require('./AppView');
	    this.appView = window.appView = new AppView();
	    this.appView.render();
	}
});
