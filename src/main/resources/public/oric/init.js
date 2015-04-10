$(document).ready( function(){
	_.templateSettings = {
			interpolate: /\{\{(.+?)\}\}/g
	};
	$.ajaxSetup({
		
		headers : {
			'X-CSRF-Token' : Cal.csrfToken
		},
		
		beforeSend: function(xhr) {
			if(Cal.csrfToken) {
				xhr.setRequestHeader('X-CSRF-TOKEN', Cal.csrfToken);
			}
		},
		
		complete: function(xhr) {
			Cal.csrfToken = xhr.getResponseHeader('X-CSRF-TOKEN');
		}
	});
});