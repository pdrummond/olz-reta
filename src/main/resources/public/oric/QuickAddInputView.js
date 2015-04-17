"use strict";

var _ = require('underscore');
var $ = require('jquery');
var Backbone = require('backbone');

module.exports = Backbone.View.extend({
	
	id: "QuickAddInputView",
	template: _.template($('#QuickAddInputViewTemplate').html()),
	
	events: {
		"keyup #quick-add-input": "onInput",
	},
		
	initialize: function() {
		
	},
	
	render: function() {
		this.$el.html(this.template());
		return this.el;
	},
	
	onInput: function(ev) {
		if(ev.keyCode == 13) {
			var content = this.$("#quick-add-input").val();
			if(content.length > 0) {
				this.$("#quick-add-input").val("");
				this.trigger("enter-pressed", content);
			}
		}
	}
	
	
});