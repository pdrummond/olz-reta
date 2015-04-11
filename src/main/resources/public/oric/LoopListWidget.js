"use strict";

var _ = require('underscore');
var $ = require('jquery');
var Backbone = require('backbone');

var OlzView = require("./OlzView");
var LoopListView = require('./LoopListView');
var LoopInputView = require('./LoopInputView');

/**
 * This view contains the LoopInputView and the LoopListView.
 * 
 * When a loop is created either through the input view or by other means (like the Create Task
 * button for instance), a NEW_LOOP message is created and the 'new-loop-message' event is triggered.
 */
module.exports = OlzView.extend({
	id:"LoopListWidget",
	template: _.template($('#LoopListWidgetTemplate').html()),

	initialize: function() {
		this.loopListView = new LoopListView();
		this.loopInputView = new LoopInputView();
		
		this.listenTo(this.loopInputView, 'enter-pressed', this.onInputViewEnterPressed);
		
	},
	
	render: function() {
		this.$el.html(this.template());		
		this.$("#loop-list-view-container").html(this.loopListView.render());
		this.$("#loop-input-view-container").html(this.loopInputView.render());		
		return this.el;
	},
	
	onInputViewEnterPressed: function(content) {
		var message = {content: content};
		this.trigger('new-loop-message', message);
	}
});