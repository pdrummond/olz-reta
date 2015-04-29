"use strict";

var _ = require('underscore');
var $ = require('jquery');
var Backbone = require('backbone');

var ChannelView = require("./ChannelView");
var MessageItemView = require("./MessageItemView");

module.exports = Backbone.View.extend({
	id:"MessageListView",	
	template: _.template($('#MessageListViewTemplate').html()),
	
	initialize: function(options) {		
		this.collection = options.collection;
		this.listenTo(this.collection, 'add', this.addMessageItemView);
		this.listenTo(this.collection, 'reset', this.addMessageItemViews);		
		this.views = [];
	},
	
	render: function() {		
		this.$el.html(this.template());
		return this.el;
	},
	
	addMessageItemViews: function(messages) {
		var self = this;
		_.each(messages.models, function(messageModel) {
			self.addMessageItemView(messageModel, {addToBottom:true});
		});
	},
	
	addMessageItemView: function(messageModel, opts) {
		var view = this.createMessageItemView(messageModel);
		if(opts && opts.addToBottom) {
			this.$("#message-list").append(view.render());
		} else {
			this.$("#message-list").prepend(view.render());
		}
		this.views.push(view);
		return view;
	},
	
	createMessageItemView: function(model) {
		var view = new MessageItemView({model: model});
		this.listenTo(view, 'message-clicked', this.onMessageClicked);
		this.listenTo(view, 'promote-to-task-clicked', this.onPromoteToTaskClicked)
		return view;
	},
	
	onMessageClicked: function(messageView) {
		this.trigger("message-clicked", messageView);
	},
	
	onPromoteToTaskClicked: function(messageView) {
		this.trigger("promote-to-task-clicked", messageView);
	},
	
	clear: function() {
		_.each(this.views, function(view) {
			view.remove();
		});		
	},
	
	getViews: function() {
		return this.views;
	}
});