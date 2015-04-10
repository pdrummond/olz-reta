'use strict'

var _ = require('underscore');
var $ = require('jquery');
var Backbone = require('backbone');

var Router = require('./Router.js');

window.csrfToken = null;

// {{ var }} and {{! escaped var }}  
_.templateSettings = {
		interpolate: /{{(.+?)}}/g,
		escape: /{{!(.+?)}}/g,
};

$.ajaxSetup({
	
	headers : {
		'X-CSRF-Token' : window.csrfToken
	},
	
	beforeSend: function(xhr) {
		if(window.csrfToken) {
			xhr.setRequestHeader('X-CSRF-TOKEN', window.csrfToken);
		}
	},
	
	complete: function(xhr) {
		window.csrfToken = xhr.getResponseHeader('X-CSRF-TOKEN');
	}
});

$(function() {
	this.router = new Router();
	Backbone.history.start({
		pushState: true,
		root     : 'loops'
	});
	
});