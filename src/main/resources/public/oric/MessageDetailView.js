"use strict";

var _ = require('underscore');
var $ = require('jquery');
var Backbone = require('backbone');

module.exports = Backbone.View.extend({

	id:"MessageDetailView",	
	template: _.template($('#MessageDetailViewTemplate').html()),
	
	events: {
		'click #message-detail-edit-button': 'onEditButtonClicked'
	},
	
	initialize: function() {
	},
	
	render: function() {
		if(this.model != null) {
			this.$el.html(this.template(this.model.attributes));
		}
		return this.el;
	},

	setModel: function(model) {
		this.model = model;
		this.listenTo(this.model, 'reset', this.render);
		this.render();
	},
	
	getMessageId: function() {
		if(this.model == null) {
			return 0;
		} else {
			return this.model.get('id');
		}
	},
	
	onEditButtonClicked: function() {
		if(this.isEditing) {
			this.isEditing = false;
			this.$("#message-detail-content").attr("contenteditable", "false");
			this.$("#message-detail-edit-button").html("Edit");
			var content = this.$("#message-detail-content").text().trim();
			this.model.set('content', content);
		} else {
			this.$("#message-detail-content").attr("contenteditable", "true");
			this.$("#message-detail-content").focus();
			this.$("#message-detail-edit-button").html("Save");
			this.isEditing = true;
		}
				
	}
});