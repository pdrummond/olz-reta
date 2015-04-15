"use strict";

var _ = require('underscore');
var $ = require('jquery');
var Backbone = require('backbone');

var OlzView = require("./OlzView");
var FilterInputView = require('./FilterInputView');
var LoopListView = require('./LoopListView');
var LoopInputView = require('./LoopInputView');

/**
 * This view contains the FilterInputView, LoopListView and the LoopInputView.
 * 
 * When a loop is created either through the input view or by other means (like the Create Task
 * button for instance), a NEW_LOOP message is created and the 'new-loop-message' event is triggered.
 */
module.exports = OlzView.extend({
	id:"LoopListWidget",
	template: _.template($('#LoopListWidgetTemplate').html()),

	initialize: function(options) {
		this.filterInputView = new FilterInputView();
		this.loopListView = new LoopListView({collection: options.collection});
		this.loopInputView = new LoopInputView();
		
		this.listenTo(this.filterInputView, 'enter-pressed', this.onFilterInputViewEnterPressed);
		this.listenTo(this.loopInputView, 'enter-pressed', this.onLoopInputViewEnterPressed);
		
	},
	
	render: function() {
		this.$el.html(this.template());		
		this.$("#filter-input-view-container").html(this.filterInputView.render());		
		this.$("#loop-list-view-container").html(this.loopListView.render());
		this.$("#loop-input-view-container").html(this.loopInputView.render());		
		return this.el;
	},
	
	onFilterInputViewEnterPressed: function(query) {
		var message = {query: query};
		this.trigger('filter-message', message);
	},
	
	onLoopInputViewEnterPressed: function(content) {
		var message = {content: content};
		this.trigger('new-loop-message', message);
	},
	
	addLoopItem: function(message) {
		this.loopListView.addLoopItem(message);
	}
});