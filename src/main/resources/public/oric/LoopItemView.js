"use strict";

var _ = require('underscore');
var $ = require('jquery');
var Backbone = require('backbone');

var OlzView = require("./OlzView");

module.exports = OlzView.extend({
	id:"LoopItemView",
	events: {
		
	},
	
	initialize: function() {
	},
	
	render: function() {		
		this.$el.html(this.getTemplate("#LoopItemViewTemplate"));
	}
});