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
(In the future site visitors may see sample search)

