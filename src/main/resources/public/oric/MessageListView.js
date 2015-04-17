"use strict";

var _ = require('underscore');
var $ = require('jquery');
var Backbone = require('backbone');

var MessageView = require("./MessageView");

module.exports = Backbone.View.extend({
	id:"LoopListView",	
	template: _.template($('#MessageListViewTemplate').html()),
	
	initialize: function(options) {
		this.collection = options.collection;
		this.listenTo(this.collection, 'add', this.addMessageView);
		this.listenTo(this.collection, 'reset', this.addMessageViews);
	},
	
	render: function() {		
		this.$el.html(this.template());
		return this.el;
	},
	
	addMessageViews: function(messages) {
		var self = this;
		_.each(messages.models, function(messageModel) {
			self.addMessageView(messageModel);
		});
	},
	
	addMessageView: function(messageModel) {
		var view = this.createMessageView(messageModel);
		this.$("#message-list").append(view.render());
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
	}
});