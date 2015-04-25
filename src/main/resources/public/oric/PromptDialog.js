'use strict';

var $ = require('jquery');
var Backbone = require('backbone');
var _ = require('underscore');
Backbone.$ = $;

var BaseDialog = require('./BaseDialog');

var DialogView;

module.exports = BaseDialog.extend({

	initialize: function(options) {
		this.view = new DialogView(options);
		BaseDialog.prototype.initialize.apply(this, [{
			title: options.title,
			message: options.message,
			animate: true,
			focusOk: false,
			preventDefaultForEnterKey: true,
			enterTriggersOk: true,
			escape: false,     
			allowCancel:true,			
			content: this.view,
		}]);   
		var self = this;
		this.listenTo(this, 'shown.bs.modal', function() {
			if(options.label) {
				self.setLabel(options.label);
			}
			self.setContent(options.content);
			if(options.message != null) {
				self.setMessage(options.message);
			}
			self.$('#content-input').focus(); 
			self.$('#content-input').select(); 
		}); 
		if(options.label) {
			self.setLabel(options.label);
		}
	},

	getContent: function() {
		return this.view.getContent();
	},

	setContent: function(content) {
		this.view.setContent(content);
	},
	
	setMessage: function(message) {
		this.view.setMessage(message);
	},
	
	setLabel: function(label) {
		this.view.setLabel(label);
	}

});

var DialogView = Backbone.View.extend({
	template: _.template($('#PromptDialogTemplate').html()),

	initialize: function(options) {
		this.options = options;
	},

	render: function() {
		this.$el.html(this.template);
		var self = this;
		this.$("#content-input").val(this.options.content);
		return this;
	},
	
	setLabel: function(label) {
		this.$("#content-label").text(label);
	},

	setContent: function(content) {
		this.$('#content-input').val(content);
	},

	getContent: function() {
		return this.$('#content-input').val();
	},
	
	setMessage: function(message) {
		this.$("#message").toggle(message != null && message.length > 0);
		this.$("#message").html(message);
	}
});

