"use strict";

var _ = require('underscore');
var $ = require('jquery');
var Backbone = require('backbone');

var OlzView = require("./OlzView");
var LoopItemView = require("./LoopItemView");

module.exports = OlzView.extend({
	id:"LoopListView",	
	template: _.template($('#LoopListViewTemplate').html()),
	
	render: function() {		
		this.$el.html(this.template());
		return this.el;
	},
	
	addLoopItem: function(message) {
		var loopItemView = new LoopItemView({message: message});
		this.$("#loop-list").append(loopItemView.render());
	}
});