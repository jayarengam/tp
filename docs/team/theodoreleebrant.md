---
layout: page
title: Theodore Leebrant's Project Portfolio Page
---

## Project: TAskmaster

TAskmaster is a desktop app for managing students, optimised for use via a Command Line Interface (CLI) while still 
having the benefits of a Graphical User Interface (GUI). If you are a CS Teaching Assistant who can type fast, 
TAskmaster can help you track your students' attendance and class participation faster than traditional GUI apps.

This application contains a central database of students, and supports operations to add, edit and delete students. 
Each entry in the database keeps track of a student's name, NUSNET ID, Telegram handle and email address.

It also supports commands to add and delete tutorial sessions. Each tutorial session contains an independent list of
student records. A Teaching Assistant (TA) has the option to mark attendances and assign class participation scores to 
each student in a session. He or she is also able to mark the attendance or assign a score to all students within a 
session in one command.

Given below are my contributions to the project.
* **New Feature**: 
  * Added a Participation Record field in the student session record, such that students' participation marks can be
  scored in every class session.
  * Adapted the file writing behaviour of the original storage to include a new line at end-of-file to conform with 
  checkstyle.
  
* **Code contributed**: 
[RepoSense link](https://nus-cs2103-ay2021s1.github.io/tp-dashboard/#breakdown=true&search=theodoreleebrant)

* **Project management**:
  * Included the CI and codecov badge inside the project.
  * Managed the overall code coverage of the project.
  * Created the pull request template which includes a checklist of what needs to be done for approval.
  * Created bug report and features request template to make it more descriptive.

* **Enhancements to existing features**:
    * Changed the address field to one that supports NUSNET ID.
      * Updated the regular expression validation for the field.
    * Changed the phone address to a telegram account.
      * Updated the input validation to one that conforms to 
      [Telegram's API](https://core.telegram.org/method/account.checkUsername).
    * Refactored Addressbook to Taskmaster.
    * Wrote additional tests for existing features to 
    increase coverage from 59.8% to 64.2% (Pull request [\#105]()).

* **Documentation**:
  * User Guide:
    * Added documentation for the features `score` and `score all`: [\#109]()
    * Fixed the overall formatting for all tables and headings, as well as broken links: [\#113](), [\#114]()
  * Developer Guide:
    * Added implementation details of the `Class Participation`.

* **Community**:
  * PRs reviewed and approved, as well as bug reported: 
  [\#26](), [\#35](), [\#51](), [\#96](), [\#105](), [\#179](), [\#181]()
  * Contributed to forum discussions 
  (examples: [(Question) 1](https://github.com/nus-cs2103-AY2021S1/forum/issues/12), 
  [(Question) 2](https://github.com/nus-cs2103-AY2021S1/forum/issues/23), 
  [(Question) 3](https://github.com/nus-cs2103-AY2021S1/forum/issues/98), 
  [(Tip) 4](https://github.com/nus-cs2103-AY2021S1/forum/issues/264)
  [(Question) 5](https://github.com/nus-cs2103-AY2021S1/forum/issues/307)
  [(Answering) 6](https://github.com/nus-cs2103-AY2021S1/forum/issues/90))
  * Reported bugs and suggestions for other teams in the class (examples: 
  [1](https://github.com/AY2021S1-CS2103-W14-3/tp/issues/112), 
  [2](https://github.com/AY2021S1-CS2103-W14-3/tp/issues/113), 
  [3](https://github.com/AY2021S1-CS2103-W14-3/tp/issues/117))

* **Tools**:
    * Managed and configured the code coverage tools (using [codecov.io](https://www.codecov.io))
    * Configured the github CI to include the codecov tool after intermittent failure
    * Configured the YAML file for code coverage settings
    
