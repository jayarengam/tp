---
layout: page
title: Theodore Leebrant's Project Portfolio Page
---

## Project: TAskmaster

TAskmaster is a desktop app for managing students, optimised for use via a Command Line Interface (CLI) while still 
having the benefits of a Graphical User Interface (GUI). If you are a CS Teaching Assistant (TA) who can type fast, 
TAskmaster can help you track your students' attendance and class participation faster than traditional GUI apps.

This application consists of two major parts:
1. A database of students. Each entry keeps track of the name of the student, his unique NUSNET ID, Telegram handle, 
as well as email address. This database supports adding, editing, and deleting students.
2. Tutorial sessions. TAs can create and delete tutorial sessions. 
Every tutorial session will have a list of student records for that particular session. 
Each student record consists of the student’s name, NUSNET ID, attendance mark, as well as participation score. 
Each session supports two operations: assigning class participation score and marking attendance of students.

Given below are my contributions to the project.
* **New Features**: 
  * Added a Participation Record, which is part of a Student Record in every class session.
    * What it does: Allow students' participation to be scored in every class session.
    * Justification: This feature is essential to the product, 
    as the TA needs to keep track of how active the student has been participating in the class.
    * Highlights (Full elaboration in the Developer Guide): 
        * Modifying the Participation Record according to the ever-changing specification was challenging, because design choices need to be considered fully as it would affect tests and documentation.
        * The choice to separate Participation Record from Student Record was done after much discussion, and resulted in beneficial effects such as decoupling.
  * Implemented the corresponding command to score the participation of the student in a session.
    * What it does: Allow TAs to score the participation of students in class.
    * Justification: This is a corresponding change to the above Participation Record, to allow end-users to
    change the Participation Record of the student.
    * Highlights:
        * Challenges were had due to the number of changes to the design of the Participation Record, causing an equivalent number of changes in the newly-made Score Command.
        * The possible pitfalls of this command have caused bug-catching to be hard to debug especially with the lack of a step debugger in Streams.
  
  
* **Code contributed**: 
[RepoSense link](https://nus-cs2103-ay2021s1.github.io/tp-dashboard/#breakdown=true&search=theodoreleebrant)

* **Project management**:
  * Set up the GitHub repository and team organization.
  * Included the CI and codecov badge inside the project.
  * Managed the overall code coverage of the project.
  * Created the pull request template which includes a checklist of what needs to be done for an approval.
  * Created bug report and features request templates to make them more descriptive.

* **Enhancements to existing features**:
    * Changed the address field to one that supports NUSNET ID.
      * Updated the regular expression validation for the field.
    * Changed the phone number to a telegram account.
      * Updated the input validation to one that conforms to 
      [Telegram's API](https://core.telegram.org/method/account.checkUsername).
    * Refactored Addressbook to TAskmaster.
    * Wrote additional tests for existing features to 
    increase coverage from 59.8% to 64.2% (Pull request [\#105](https://github.com/AY2021S1-CS2103-F09-1/tp/pull/105)).
    * Adapted the file writing behaviour of the original storage to include a new line at end-of-file to conform with 
      checkstyle.
        * Justification: Save files were violating CheckStyle tests, and changing this behaviour resulted in a higher
        quality of life for the developers as this file is created automatically and needs to be pushed to the repository.

* **Documentation**:
  * User Guide:
    * Added documentation for the features `score` and `score all`: [\#109](https://github.com/AY2021S1-CS2103-F09-1/tp/pull/109)
    * Fixed the overall formatting for all tables and headings, as well as broken links: [\#113](https://github.com/AY2021S1-CS2103-F09-1/tp/pull/113), [\#114](https://github.com/AY2021S1-CS2103-F09-1/tp/pull/114)
  * Developer Guide:
    * Added implementation details, diagram, as well as manual test cases of Class Participation Scoring (`score` command)

* **Community**:
  * PRs reviewed and approved [[Link]](https://github.com/AY2021S1-CS2103-F09-1/tp/pulls?q=is%3Apr+is%3Aclosed+reviewed-by%3Atheodoreleebrant)
  , as well as reported bugs and suggested features in the team repository [[Link]](https://github.com/AY2021S1-CS2103-F09-1/tp/issues?q=is%3Aissue+author:theodoreleebrant+is%3Aclosed)
  .
  * Contributed to forum discussions 
  (examples: [(Question) 1](https://github.com/nus-cs2103-AY2021S1/forum/issues/12), 
  [(Question) 2](https://github.com/nus-cs2103-AY2021S1/forum/issues/23), 
  [(Question) 3](https://github.com/nus-cs2103-AY2021S1/forum/issues/98), 
  [(Tip) 4](https://github.com/nus-cs2103-AY2021S1/forum/issues/264),
  [(Question) 5](https://github.com/nus-cs2103-AY2021S1/forum/issues/307),
  [(Answering) 6](https://github.com/nus-cs2103-AY2021S1/forum/issues/90))
  * Reported bugs and suggestions for other teams in the class (examples: 
  [1](https://github.com/AY2021S1-CS2103-W14-3/tp/issues/112), 
  [2](https://github.com/AY2021S1-CS2103-W14-3/tp/issues/113), 
  [3](https://github.com/AY2021S1-CS2103-W14-3/tp/issues/117))

* **Tools**:
    * Managed and configured the code coverage tools (using [codecov.io](https://www.codecov.io))
    * Configured the github CI to include the codecov tool after intermittent failure
    * Configured the YAML file for code coverage settings
    
