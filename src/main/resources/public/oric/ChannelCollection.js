var Backbone = require('backbone');
var _ = require('underscore');

var ChannelModel = require('./ChannelModel');

module.exports = Backbone.Collection.extend({
	model: ChannelModel,

	comparator : function(message) {
		return -message.get('createdAt');
	},

	url: function() {
		return "/rest/channels";
	}
});
