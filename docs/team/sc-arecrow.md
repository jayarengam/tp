---
layout: page
title: Goh Siau Chiak's Project Portfolio Page
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

- **New Feature:** Designed and implemented the student record and record list contained in a session. 
    - What it does: Allows a TA to have a dynamic record of students who are enrolled in a particular session that
    updates when certain changes are made, such as marking a student's attendance or awarding class participation score.
    Each record contains the name and NUSNET ID of the represented student, their attendance and class participation
    score. \
    **Important**: the student record list in each session is **independent** of the student list maintained by
    TAskmaster.
    - Justification: This feature is essential to the product as a TA should be able to see and change certain details
    of the student records in a session setting, without modifying the student list separately maintained by TAskmaster.
    - Highlights: This enhancement requires the implementation of an immutable `StudentRecord` class and a
    `StudentRecordList` as a data structure that supports the controlled addition and editing of student records. This
    was challenging as it required an in-depth analysis of design alternatives (discussion found in the [developer guide](../DeveloperGuide.md#studentrecordlist))
    to how the `StudentRecord` and `StudentRecordList` classes should be implemented.
    - Credits: The design of `StudentRecordList` was inspired by the design of `UniquePersonList` in AB3.
            
- **New Feature:** Added the ability to mark a student's attendance, or all students' attendances at once, in a
particular session.
    - What it does: Allows a TA to track who is present at the session and who is absent.
    - Justification: This feature enables the TA to mark class attendance efficiently and quickly move on with the
    lesson proper. TAskmaster will save the attendance records for the TA to view at his leisure after the class has
    ended, saving the TA from having to juggle between physical or slower digital means of taking attendance and 
    teaching the class. 
    - Highlights: This enhancement requires some contemplation to be made on the implementation of the mark attendance method.
    This was challenging as it required an in-depth analysis and consideration on how to reconcile the different ways
    that the mark command vs the mark attendance method identifies which record is supposed to be marked. 

- **Code contributed:** [RepoSense link](https://nus-cs2103-ay2021s1.github.io/tp-dashboard/#breakdown=true&search=sc-arecrow)

- **Project Management:**
  * Managed releases `v1.2` - `v1.3` (2 releases) on GitHub
  * Authored, tracked and closed issues on Github
  * Reviewed more than 30 PRs on GitHub

- **Enhancements to existing features:**
    - Improved the GUI in `v1.4` by fixing some bugs related to resizing of the application window, and changing the look of the
    following components: (PR [#170](https://github.com/AY2021S1-CS2103-F09-1/tp/pull/170))
        - Student List button
        - session buttons
        - student card (inside the list)
        - student record card (inside the list)
    - Redesigned `Attendance` and related classes in `v1.2` to `StudentRecord` in `v1.3` ([#77](https://github.com/AY2021S1-CS2103-F09-1/tp/pull/77))
    - Refactor names to update AB3 code to TAskmaster code: (PR [#43](https://github.com/AY2021S1-CS2103-F09-1/tp/pull/43))
        - from `AddressBook` to `Taskmaster`
        - from `Person` to `Taskmaster`

- **Documentation:**
    - User Guide:
        - Added documentation for the `mark` and `mark all` features.
        - Updated User Guide to resolve several documentation flaws pointed out during `PE-D` (PR [#166](https://github.com/AY2021S1-CS2103-F09-1/tp/pull/166))
    
    - Developer Guide:
        - Added implementation details for `StudentRecord` and `StudentRecordList`, including: (PR [#79](https://github.com/AY2021S1-CS2103-F09-1/tp/pull/79) and [#199](https://github.com/AY2021S1-CS2103-F09-1/tp/pull/199))
            - class diagrams
            - sequence diagrams
            - design alternatives 

- **Community:**
    - PRs reviewed and [approved](https://github.com/AY2021S1-CS2103-F09-1/tp/pulls?q=is%3Apr+is%3Aclosed+reviewed-by%3Asc-arecrow+)
