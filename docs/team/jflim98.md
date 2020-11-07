---
layout: page
title: Lim Jin Feng's Project Portfolio Page
---

## Project: TAskmaster

TAskmaster is a desktop app for managing students, optimised for use
via a Command Line Interface (CLI) while still having the benefits of
a Graphical User Interface (GUI). If you are a CS Teaching Assistant
who can type fast, TAskmaster can help you track your students'
attendance and class participation faster than traditional GUI apps.

Given below are my contributions to the project.

- **New feature:** `RandomStudentCommand`
    - What it does: Allows a TA to get a random student from the current session directly. 
    - Justification: With this feature, the TA does not have to think of a random name on the spot, which is susceptible
    to human bias, resulting in some students being called more often than others.
    - Highlights: While this feature was not particularly tough to implement, implementing the test for this feature 
    was challenging as unit tests for methods involving random generation was not covered in class. I ended up having to
    find a way to synchronise the seed of the random generator of the command with the test's random generator, while
    allowing the random generator to be initialised with a different seed each time it is called by the user.
    
- **Code contributed:** [RepoSense Link](https://nus-cs2103-ay2021s1.github.io/tp-dashboard/#breakdown=true&search=jflim98)

- **Project Management:**
  - Authored, tracked and closed issues on Github
  - Reviewed PRs on GitHub
  
- **Enhancements to existing features:**
    - Augmented the GUI for all major changes of TAskmaster. This includes:
        - Designing and actualising UI mockups for all iterations
        - Designing and coding FXML files and their relevant java code for all iterations
        - Implementing the ability to display the attendance list (v1.2)
        - Accommodating the addition of Sessions (v1.3)
            - Allowing sessions to be switched with the use of clicks
            - Cohering the `goto` and `list-students` command to make it function seamlessly with the GUI
        - Adding a display to notify the view the GUI is currently on (v1.4)
        - Highlights: JavaFX is badly documented with blurred lines between FXML and JavaFX code found online. It was
        challenging to implement a GUI with minimal experience (only from iP) for such a project. Software design patterns 
        used for GUI such as the Observer Pattern was alien to us at the start of this module and was required in our case
        as we implemented on click events in the GUI itself.
    - Implemented and fixed various existing tests.
    - Fixed various bugs in TAskmaster.
    
- **Documentation:**
    - User Guide:
        - Updated documentation for `find-students` and `list-students`.
        - Added documentation for `random-student`
    - Developer Guide:
        - Added implementation details for UI.
        
- **Community:**
    - PRs reviewed and [approved](https://github.com/AY2021S1-CS2103-F09-1/tp/pulls?q=is%3Apr+is%3Aclosed+reviewed-by%3Ajflim98)
