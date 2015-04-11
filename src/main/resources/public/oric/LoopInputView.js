"use strict";

var _ = require('underscore');
var $ = require('jquery');
var Backbone = require('backbone');

var OlzView = require("./OlzView");

/**
 * The LoopInputView is a contenteditable that allows the user to create new loop messages.  When the user 
 * presses ENTER an 'enter-pressed' event is fired.
 */
module.exports = OlzView.extend({
	
	id: "LoopInputView",
	template: _.template($('#LoopInputViewTemplate').html()),
	
	events: {
		"keyup #new-loop-input": "onInput",
	},
		
	initialize: function() {
		
	},
	
	render: function() {
		this.$el.html(this.template());
		return this.el;
	},
	
	onInput: function(ev) {
		if(ev.keyCode == 13) {
			var content = this.$("#new-loop-input").val();
			if(content.length > 0) {
				this.$("#new-loop-input").val("");
				this.trigger("enter-pressed", content);
			}
		}
	}
	
	
});