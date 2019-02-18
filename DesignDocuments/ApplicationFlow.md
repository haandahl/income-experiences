# Income Experiences Application Flow

### Student User Sign In
1. User chooses Sign In from the nav bar on the About page.
1. User enters school ID on form and submits.
1. If user is authenticated, the server will handle allowing access to read and search
   all pages.  (Confirm? --JDBCRealm used for authentication (schools, schools_roles, and roles tables).)

### Registered User Sign In
1. User chooses Sign In from the nav bar on the About page.
1. User enters username (or email) and password on form and submits.
1. If user is authenticated, the server will handle allowing access to read, search, and comment
   all pages as applicable.  Admin will have access to one more page with edit and 
   remove capabilities as well.  
   (Confirm? --JDBCRealm used for authentication (users, users_roles, and roles tables).)

### User Sign Up
1. User chooses Sign Up from the nav bar on the About page.
1. User fills out simple form and selects Next.
1. Request goes to sign up servlet.
1. Servlet creates a user object, checks for duplicate username or email 
and creates a user in the database if there is none.
1. Servlet directs user back to sign up dialog if necessary or presents 
the intake survey if initial sign up was successful.

### User Intake Survey
1. This is accessed only from the Sign Up dialog (or Sign in if a user
previously left the site between completing the sign in information 
and the intake survey). (In the future this may also be required of 
users sustaining registration more than one year.)
1. User completes all fields and selects Review Answers.
1. Servlet validates the form and displays feedback to the user or 
directs the user to a preview JSP.
1. User may select Edit to return to the form (with information saved) or 
Submit for final submittal of the survey (no edits).
1. Servlet creates a survey object and creates a survey in the database.
1. Servlet displays thank you message to user and directs them to 
their profile page.

### User Financial Story
1. User sees instructions and textarea input on their profile 
page. 
1. User completes their story and clicks Add Your Story.
1. Servlet adds story to database.  (Need to decide whether it is
part of the user or a separate entity.)
1. Servlet directs the user to their now-complete profile page and 
displays a thank you message.

### Search Experiences
1. Signed in user chooses Experiences from the nav bar on any page.
(In the future the Experiences page may be available to visitors
with sample data only.)  
1. User may enter part of a career name.
1. If the user enters part of a career name, the select box on the form will
populate with matching careers from BLS.
1. The user either selects a matching career or, if simply searching by income,
enters an income.
1. The user enters a family size and submits the form.
1. A servlet validates the form and gives a message back to the user if necessary.
1. Otherwise, the servlet directs the user to the Experiences Results JSP.  There, 
they will see summary charts. Advanced users will also see a list of financial stories.
1. Advanced users can access another user's profile by clicking on the link shown with 
their story.

### Explore Forum
1. Signed in user chooses Forum from the nav bar on any page.
1. The JSP displays threads by title.
1. The user can view a specific thread by clicking on the forum icon or can filter the 
threads by searching on a keyword.
1. The user can view a contributor's profile by clicking their username.

### Add Forum Topic
1. An advanced user navigates to Forum (see Explore Forum) and clicks Add a Topic.
1. A simple form (not yet designed) will be presented on the page.
1. The user enters a title (topic) and body, then submits.
1. A servlet validates that a topic was entered and directs the user back to the
Forum JSP, with their topic now added.

### Reply to a Forum Topic
1. An advanced user navigates to a thread (see Explore Forum).
1. The user clicks reply.
1. A simple textbox form will be presented to the user (not yet designed).
1. The user enters their reply, then submits.
1. A servlet validates that a reply was entered and directs the user back to the 
thread JSP, with their reply now added.

### Admin - Edit or Remove Text Entries
1. The administrator can navigate to any site content and click its edit icon 
(not currently pictured in screen designs).
1. An edit form will display (not yet designed) the content where the admin can
edit it.
1. The admin chooses Save Changes or Remove Item.
1. A servlet coordinates the change and directs the admin back to original page, 
now showing (edited) or (removed) where the change was made.

### Admin - Change User Status
1. The administrator selects Admin from the Nav bar, visible from any page if 
the admin is logged in.
1. The JSP displays a summary of active users with content that has been modified or 
removed by admin.
1. The admin can click the "original content" row of the table to see what the problems
have been.
1. The admin clicks "Block" or "Block and Remove Data".
1. The servlet accepts the request, coordinates changes to the database, and directs
the admin back to the Admin page with a confirmation message displayed.

### Log Out
1. The user selects Log Out from the nav menu on any page.
1. The user is directed back to the About page once logged out.

### Flagging Content and Reviewing Flagged Content
<p>These user stories are not yet incorporated into the application but may be
build out.</p>