var Backbone = require('backbone');
var _ = require('underscore');

var MessageModel = require('./MessageModel');

module.exports = Backbone.Collection.extend({
	model: MessageModel,

	comparator : function(message) {
		return -message.get('createdAt');
	},
	
	parse: function(response, options) {
		this.showMoreDate = response.showMoreDate;
		this.noMoreMessages = response.noMoreMessages;
		return response.messages;
	},

	url: function() {
		var url = "/rest/messages";
		var ch = "?";
		if(this.showMoreDate) {
			url += ch + 'fromDate=' + this.showMoreDate;
			ch = "&";
		}
		return url;
	}
});
