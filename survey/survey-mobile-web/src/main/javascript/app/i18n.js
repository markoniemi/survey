app.config(function($translateProvider) {
	$translateProvider.translations('en', {
		// common
		SURVEY : 'Survey',
		DELETE : 'Delete',
		EDIT : 'Edit',
		SUBMIT : 'Submit',
		CANCEL : 'Cancel',
		// users
		USERS : 'Users',
		USER : 'User',
		USERNAME : 'Username',
		PASSWORD : 'Password',
		REPEAT_PASSWORD : 'Repeat password',
		EMAIL : 'Email',
		ROLE : 'Role',
		ACTION : 'Action',
		ADD_USER : 'Add User',
		ADD_USER_ERROR : 'Unable to save user.',
		ROLE_USER : 'User',
		ROLE_ADMIN : 'Admin',
		// files
		FILES : 'Files',
		// polls
		POLLS : 'Polls',
		POLL : 'Poll',
		POLL_NAME : 'Name',
		ADD_POLL : 'Add Poll',
		QUESTION_TYPE : 'Type',
		QUESTION_TEXT : 'Text',
		ADD_QUESTION : 'Add Question',
		TYPE_LABEL : 'Label',
		TYPE_TEXT : 'Text',
		TYPE_BOOLEAN : 'Boolean',
		ADD_POLL_ERROR : 'Unable to save poll.',
		// about
		ABOUT : 'About',
		COMPONENT : 'Component',
		VERSION : 'Version'
	});
	$translateProvider.preferredLanguage('en');
});
