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
		'click #create-channel-menu-item': 'onCreateChannelMenuItemClicked',		
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
		this.$("#selected-channel-title").html(this.model != null ? this.model.get('title') : "No Channel");
		return this.el;
	},
	
	addChannelMenuItemView(model) {
		var view = new ChannelMenuItemView({model:model});
		this.$("#channel-list").append(view.render());
		this.views.push(view);
	},
	
	onCreateChannelMenuItemClicked: function() {
		var self = this;
		this.showPromptDialog('Create Channel', {}, function(channelTitle) {
			self.trigger('create-channel', channelTitle);
		});
	},

	getCurrentChannelId: function() {
		return this.model.get('id');
	},	
});