"use strict";

var _ = require('underscore');
var $ = require('jquery');
var Backbone = require('backbone');

var OlzView = require("./OlzView");
var LoopItemView = require("./LoopItemView");

module.exports = OlzView.extend({
	id:"LoopListView",	
	template: _.template($('#LoopListViewTemplate').html()),
	
	initialize: function(options) {
		this.collection = options.collection;
		this.listenTo(this.collection, 'add', this.addLoopItem);
		this.listenTo(this.collection, 'reset', this.addLoopItems);
	},
	
	render: function() {		
		this.$el.html(this.template());
		return this.el;
	},
	
	addLoopItems: function(loopItems) {
		var self = this;
		_.each(loopItems.models, function(loopItem) {
			self.addLoopItem(loopItem.attributes);
		});
	},
	
	addLoopItem: function(message) {
		var loopItemView = new LoopItemView({message: message});
		this.$("#loop-list").append(loopItemView.render());
	}
});