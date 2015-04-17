"use strict";

var _ = require('underscore');
var $ = require('jquery');
var Backbone = require('backbone');


module.exports = Backbone.View.extend({
	id:"TabView",
	template: _.template($('#TabViewTemplate').html()),

	initialize: function(options) {
		
	},
	
	render: function() {
		this.$el.html(this.template());		
		return this.el;
	},
	
});