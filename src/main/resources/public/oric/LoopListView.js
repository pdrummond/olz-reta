"use strict";

var _ = require('underscore');
var $ = require('jquery');
var Backbone = require('backbone');

var OlzView = require("./OlzView");

module.exports = OlzView.extend({
	id:"LoopListView",
	template: _.template($('#LoopListViewTemplate').html()),
	
	render: function() {		
		this.$el.html(this.template());
	}
});