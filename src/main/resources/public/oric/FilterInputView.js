"use strict";

var _ = require('underscore');
var $ = require('jquery');
var Backbone = require('backbone');

module.exports = Backbone.View.extend({
	
	id: "FilterInputView",
	template: _.template($('#FilterInputViewTemplate').html()),
	
	events: {
		"keyup #filter-input": "onInput",
		"click #all-filter-button": "onAllFilterButtonClicked",
		"click #chats-filter-button": "onChatsFilterButtonClicked",
		"click #tasks-filter-button": "onTasksFilterButtonClicked",
		"click #articles-filter-button": "onArticlesFilterButtonClicked",
		"click #channels-filter-button": "onChannelsFilterButtonClicked",
		"click #labels-filter-button": "onLabelsFilterButtonClicked",
		"click #milestones-filter-button": "onMilestonesFilterButtonClicked",
	},
		
	initialize: function(options) {
		this.appView = options.appView;
	},
	
	setFilter: function(value) {
		this.$("#filter-input").val(value);
	},
	
	render: function() {
		this.$el.html(this.template());		
		return this.el;
	},
	
	onInput: function(ev) {
		if(ev.keyCode == 13) {
			var content = this.$("#filter-input").val();
			this.trigger("enter-pressed", content);
		}
	},
	
	onAllFilterButtonClicked: function() {
		this.$(".filter-button").removeClass("active");
		this.$("#all-filter-button").addClass("active");
		this.appView.setFilter("");
	},
	
	onChatsFilterButtonClicked: function() {
		this.$(".filter-button").removeClass("active");
		this.$("#chats-filter-button").addClass("active");
		this.appView.setFilter("is:chat");
	},
	
	onTasksFilterButtonClicked: function() {
		this.$(".filter-button").removeClass("active");
		this.$("#tasks-filter-button").addClass("active");
		this.appView.setFilter("is:task");
	},
	
	onArticlesFilterButtonClicked: function() {
		this.$(".filter-button").removeClass("active");
		this.$("#articles-filter-button").addClass("active");
		this.appView.setFilter("is:article");
	},
	
	onChannelsFilterButtonClicked: function() {
		this.$(".filter-button").removeClass("active");
		this.$("#channels-filter-button").addClass("active");
		this.appView.setFilter("is:channel");
	},
	
	onLabelsFilterButtonClicked: function() {
		this.$(".filter-button").removeClass("active");
		this.$("#labels-filter-button").addClass("active");
		this.appView.setFilter("is:label");
	},
	
	onMilestonesFilterButtonClicked: function() {
		this.$(".filter-button").removeClass("active");
		this.$("#milestones-filter-button").addClass("active");
		this.appView.setFilter("is:milestone");
	},
});