"use strict";

var _ = require('underscore');
var $ = require('jquery');
var Backbone = require('backbone');

var MessageView = require("./MessageView");

module.exports = Backbone.View.extend({
	id:"MessageListView",	
	template: _.template($('#MessageListViewTemplate').html()),
	
	initialize: function(options) {
		this.collection = options.collection;
		this.listenTo(this.collection, 'add', this.addMessageView);
		this.listenTo(this.collection, 'reset', this.addMessageViews);
		this.messageViews = [];
	},
	
	render: function() {		
		this.$el.html(this.template());
		return this.el;
	},
	
	addMessageViews: function(messages) {
		var self = this;
		_.each(messages.models, function(messageModel) {
			self.addMessageView(messageModel, {addToBottom:true});
		});
	},
	
	addMessageView: function(messageModel, opts) {
		var view = this.createMessageView(messageModel);
		if(opts && opts.addToBottom) {
			this.$("#message-list").append(view.render());
		} else {
			this.$("#message-list").prepend(view.render());
		}
		this.messageViews.push(view);
		return view;
	},
	
	createMessageView: function(model) {
		var view;
		switch(model.get('messageType')) {
		case "CHAT_MESSAGE":
			view = new MessageView({model: model});
			break;
		}
		return view;
	},
	
	clear: function() {
		_.each(this.messageViews, function(view) {
			view.remove();
		});		
	},
});