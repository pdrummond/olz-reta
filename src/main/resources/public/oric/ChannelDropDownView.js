"use strict";

var _ = require('underscore');
var $ = require('jquery');
var Backbone = require('backbone');

var OlzBaseView = require('./OlzBaseView');
var ChannelMenuItemView = require('./ChannelMenuItemView');

module.exports = OlzBaseView.extend({
	
	id:"ChannelDropDownView",
	tagName: 'span',
	template: _.template($('#ChannelDropDownViewTemplate').html()),
	
	events: {
		'click #home-channel-menu-item': 'onHomeChannelMenuItemClicked',
		'click #create-channel-menu-item': 'onCreateChannelMenuItemClicked'		
	},
	
	initialize: function(options) {
		this.collection = options.collection;
		this.listenTo(this.collection, 'reset', this.render);
		this.views = [];
	},
		
	render: function() {
		var self = this;
		this.$el.html(this.template());
		this.collection.each(function(model) {
			self.addChannelMenuItemView(model);
		});		
		this.$("#selected-channel-title").html(this.model != null ? this.model.get('title') : "HOME");
		$('.dropdown-toggle').dropdown();
		return this.el;
	},
	
	addChannelMenuItemView(model) {
		var view = new ChannelMenuItemView({model:model});
		this.listenTo(view, 'channel-clicked', this.onChannelMenuItemClicked);
		this.$("#channel-list").append(view.render());
		this.views.push(view);
	},
	
	onHomeChannelMenuItemClicked: function() {
		this.trigger('channel-clicked', null);
	},
	
	onCreateChannelMenuItemClicked: function() {
		var self = this;
		this.showPromptDialog('Create Channel', {}, function(channelTitle) {
			self.trigger('create-channel', channelTitle);
		});
	},
	
	onChannelMenuItemClicked: function(model) {
		this.model = model;
		this.render();
		this.trigger('channel-clicked', model);
	},

	getCurrentChannelId: function() {
		return this.model.get('id');
	},	
});