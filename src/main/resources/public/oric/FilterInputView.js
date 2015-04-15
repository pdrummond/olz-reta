"use strict";

var _ = require('underscore');
var $ = require('jquery');
var Backbone = require('backbone');

var OlzView = require("./OlzView");

module.exports = OlzView.extend({
	
	id: "FilterInputView",
	template: _.template($('#FilterInputViewTemplate').html()),
	
	events: {
		"keyup #filter-input": "onInput",
	},
		
	initialize: function() {
		
	},
	
	render: function() {
		this.$el.html(this.template());
		return this.el;
	},
	
	onInput: function(ev) {
		if(ev.keyCode == 13) {
			var content = this.$("#filter-input").val();
			if(content.length > 0) {
				this.$("#filter-input").val("");
				this.trigger("enter-pressed", content);
			}
		}
	}
	
	
});