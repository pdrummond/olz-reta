var Backbone = require('backbone');
var _ = require('underscore');

var MessageModel = require('./MessageModel');

module.exports = Backbone.Collection.extend({
	model: MessageModel,

	comparator : function(loopItem) {
		return loopItem.get('createdAt');
	},

	url: function() {
		return "/rest/messages";
	}
});
