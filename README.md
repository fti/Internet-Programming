Java Web Programming Project Implementation

In this project, you will implement a web application for submitting, reviewing and editing scientific conference papers. Your web application will provide the following five web pages for the authors, the reviewers and the editors:

1. Main Login Page: This page asks for username and password. There will be four buttons: New User, Author Login, Editor Login, Reviewer Login. If the user hasn’t registered yet, he should push the “New User” button in order to access the Registration Page. Registered users may access to Author Page, Reviewer Page or Editor Page by providing username and password and pushing the related button. Note that any registered user can access the Author Page. The Reviewer Page can only be accessed by the users that have reviewing capability. Only one predetermined user can access the Editor Page.


2. Registration Page: In this page, the new user gives his name, surname, username (e-mail address) and password. Also he specifies whether he wants to be a reviewer or not. All the fields are mandatory; empty or malformed data would not be accepted. Username should be a valid e- mail address with an ‘@’ character between two strings. When the user pushes the Register button, registration would be completed, if there is no other user with the same username. System will give “reviewer capability” to the users that prefer to be a reviewer in this page. After completion of the registration, user should be returned to the Main Login Page.


3. Author Page: In this page, the author would be able to enter the title of his paper and submit it in pdf format. A message like “Dear name surname, please enter the title and the full path name of your paper” should appear at the upper part of the page. There should be a text field for entering the title of the paper, another text field for the filename, a “Browse” button for browsing and selecting the file from the user’s computer and a “Submit” button for submitting the paper. Upon the successful completion of the submission process, a message like “Dear name surname, your paper paper_title is submitted successfully” should appear.


4. Editor Page: In this page, titles and authors of all submitted papers will be listed. For each paper, the editor would be able to assign two reviewers among all registered reviewer candidates. (Of course, the system should not allow the author to be assigned as the reviewer of his own paper.) As the editor selects the paper and two reviewers and push the “Assign Reviewer” button, the selected paper will be assigned to those reviewers.


5. Reviewer Page: In this page, the paper(s) assigned to the reviewer will be listed. Reviewer would be able to click on the title of the paper and download the paper in pdf format to his/her PC. You may assume that the reviewer is going to send his/her comments via e-mail.
For each of the Author Page, Editor Page and Reviewer Page, there should also be a “Logout” button.

In Reviewer Page: You should add three fields. First field is for overall rating of the paper (between 1-very poor and 5-excellent). Second field is for comments to editor, and third field is for comments to author. You should also add a button for submitting review. After the submission, the editor would be able to observe reviewer comments and rating.

In Editor Page: The editor should be able to observe reviewer comments and ratings for each paper. And he should be able to accept or reject any paper.

In Author Page: The author should be able to observe the status of his/her submitted paper(s). (under review / accepted / rejected). If the status is “accepted” or “rejected”, there should appear a link for observing reviewer comments.
