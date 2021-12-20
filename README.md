
# A Test of My Scala Skills

A test of my scala skills for an interview test; a simple project to get site
titles by URL.

## Test Details

Create a web form that allows a user to type in a url to any website and have
it then output the Title of that website on the same page. For example, if I
typed in CNN.COM into the text input of the web page, it would spit out 

CNN - Breaking News, Latest News and Videos

Use the Play framework and Scala for the backend title collecting API. It's
probably easiest to clone from a simple seed project. 

For the frontend, use ajax requests to submit the web form to the backend, and
style with bootstrap (any version)

Please think through and handle edge cases and validation carefully.

Provide a running example and source code. Heroku is an fairly easy option to
get a Scala application running

Bonus points:
- Use React or any other front end web framework for the web form (Vue,
  Angular, Ember, etc.)

## Feature Requirements

### Summary

_(Some redundancy on my part to go over details in my head)._

A web page built with bootstrap that has a form with one input field. This
field allows the user to enter a URL and after submission the page returns
the title of the web page (Page layout TBD).

Since this is a test of Scala, the form will send an Ajax request to the
backend where Scala will handle the request. This is where the logic to
retrieve the web page title will live and be returned.

I'll be handling the Ajax request with Vue (Been enjoying Vue lately).

### Requirement Details

Some of this may change or the order of things may change. These were
just initial thoughts.

* Review and install Scala locally.
* Setup a Scala project including Play framework using one of the seeds..
* Create git repo and host on gitlab or github. 
* Create a web page that includes both bootstrap and Vue (possiblly integrated
  into Play's view layer).
* Create page content (layout TBD)
    * Add a form and a field that the user uses to enter an URL
    * Create an area for the site's title to be displayed
    * Setup Vue to dynamically update the title section using a prototype API.
* Create Play endpoint and anything else to connect the request to Play to the
  place where Scala logic will live (bit of research here TBD. Is Play MVC??).
* Code the Scala logic to retrieve the title from the website that the user
  enters and return this from the API request. (bit of research here TBD)
    * Make sure to include validation and error handling. Examples:
        * Is the URL valid?
        * Did the site respond?
        * Does the site's html include a title?
* Connect Vue request to the Play endpoint and scala logic.
* Test
* Setup environment to host product. Heroku was suggested as a simple
  possibility.
