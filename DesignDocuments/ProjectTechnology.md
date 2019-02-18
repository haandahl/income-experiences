# Project Technologies/Techniques 

<p>This is an original, individual project being completed for Enterprise Java at Madison Area Technical College. 
The technologies list is adapted from instructor Paula Waite's sample list.</p>

* Security/Authentication
  * Tomcat's JDBC Realm Authentication
  * Visitor (no login): view admin page (possibly sample information on Experiences or Forum page)
  * Student role: read all data
  * Basic member: search survey data, add story (and upgrade membership)
  * Advanced member: adds to basic member functions -- search financial stories and forum, 
  comment on stories and forum threads, add forum topics, edit their own financial story.
  * Admin: create/read all data, update text data or user status, delete text data, delete user and all associated data
* Database
  * MySQL
  * Store users, roles, surveys, financial stories, forum topics, any user comments, etc.
* ORM Framework
  * Hibernate 5 (Confirm version?)
* Dependency Management
  * Maven
* Web Services consumed using Java
  * BLS API for obtaining careers and their median salaries
* CSS 
  * Bootstrap 4
* Data Validation (These are Paula's, maybe they apply?)
  * Bootstrap Validator for front end
  * Explore Hibernate's validation  
* Logging
  * Configurable logging using Log4J2. In production, only errors will normally be logged, but logging at a debug level can be turned on to facilitate trouble-shooting. 
* Hosting
  * AWS
* Independent Research Topic/s
  * Hibernate Search
  * Try to add another - consider tools for creating graphs?
* Unit Testing
  * JUnit tests to achieve 80%+ code coverage (Paula's goal)
* IDE: IntelliJ IDEA