"use strict";

var _ = require('underscore');
var $ = require('jquery');
var Backbone = require('backbone');

module.exports = Backbone.View.extend({
	
	id: "FilterInputView",
	template: _.template($('#FilterInputViewTemplate').html()),
	
	events: {
		"keyup #filter-input": "onInput",
	},
		
	initialize: function() {
		
	},
	
	setFilter: function(value) {
		this.$("#filter-input").val(value);
	},
	
	render: function() {
		this.$el.html(this.template());		
		return this.el;
	},
	
	onInput: function(ev) {
		if(ev.keyCode == 13) {
			var content = this.$("#filter-input").val();
			this.trigger("enter-pressed", content);
		}
	}
	
	
});