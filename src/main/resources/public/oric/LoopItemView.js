"use strict";

var _ = require('underscore');
var $ = require('jquery');
var Backbone = require('backbone');

var OlzView = require("./OlzView");

module.exports = OlzView.extend({
	
	id:"LoopItemView",
	
	tagName:"li",
	
	template: _.template($('#LoopItemViewTemplate').html()),
	
	initialize: function(options) {
		this.message = options.message;
	},
		
	render: function() {		
		this.$el.html(this.template(this.message));
		return this.el;
	}
});