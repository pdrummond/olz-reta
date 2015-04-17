"use strict";

var _ = require('underscore');
var $ = require('jquery');
var Backbone = require('backbone');

var FilterInputView = require('./FilterInputView');
var MessageListView = require('./MessageListView');
var QuickAddInputView = require('./QuickAddInputView');

module.exports = Backbone.View.extend({
	id:"TabView",
	template: _.template($('#TabViewTemplate').html()),

	initialize: function(options) {
		this.filterInputView = new FilterInputView();
		this.messageListView = new MessageListView({collection: options.collection});
		this.quickAddInputView = new QuickAddInputView();
		
		this.listenTo(this.filterInputView, 'enter-pressed', this.onFilterInputViewEnterPressed);
		this.listenTo(this.quickAddInputView, 'enter-pressed', this.onQuickAddInputViewEnterPressed);
		
	},
	
	render: function() {
		this.$el.html(this.template());		
		this.$("#filter-input-view-container").html(this.filterInputView.render());		
		this.$("#message-list-view-container").html(this.messageListView.render());
		this.$("#quick-add-input-view-container").html(this.quickAddInputView.render());		
		return this.el;
	},
	
	onFilterInputViewEnterPressed: function(query) {
		var message = {query: query};
		this.trigger('filter-message', message);
	},
	
	onQuickAddInputViewEnterPressed: function(content) {
		var message = {content: content};
		this.trigger('chat-message', message);
	},
	
	addMessage: function(messageModel) {
		return this.messageListView.addMessageView(messageModel);
	}
});