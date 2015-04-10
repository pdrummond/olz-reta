"use strict";

var _ = require('underscore');
var $ = require('jquery');
var Backbone = require('backbone');

module.exports = Backbone.View.extend({

	events: {
		
	},
	
	initialize: function() {
	},
	
	render: function() {
		if(this.template == null) {
			this.template = _.template($('#LoopListViewTemplate').html());
		}
		this.$el.html(this.template());
	}
});