---
layout: page
title: Jaya Rengam's Project Portfolio Page
---

## Project: TAskmaster

TAskmaster is a desktop app for managing students, optimised for use
via a Command Line Interface (CLI) while still having the benefits of
a Graphical User Interface (GUI). If you are a CS Teaching Assistant
who can type fast, TAskmaster can help you track your students'
attendance and class participation faster than traditional GUI apps.

Given below are my contributions to the project.

* **New Feature**: Storage for `Attendance`
  * This feature was replaced by the feature below, also developed by me.

* **New Feature**: Storage for `SessionList`
  * What it does: saves the `SessionList` of the Taskmaster to a file every time it is modified; the `SessionList` is then loaded from the file on startup.
  * Justification: This feature is essential for practical use of the application as it is expected that a user will not have it open all the time, but would still want to save their records when closing and reopening the app. 
  * Highlights: This enhancement required an good understanding of existing `Storage` code to convert `SessionList` classes to their Json representations. Significant modification to existing test code was also done (especially in `TypicalStudents.java`)
  * Credits: The design of this feature were adapted from existing Storage code.

* **New Feature**: `LowestScoreCommand`
  * What it does: in a `Session`, displays all the students with the lowest class participation score
  * Justification: This feature is useful for TAs, especially if they have classes with many students and cannot afford to scroll through their list to call a student who does not have class participation.
  * Highlights: This enhancement required a substantial refactoring of Model code as the `ObservableList<StudentRecord` passed to the UI was previously not modifiable (to set a predicate).
    The change of the relevant field in Model triggered a few errors that had to be fixed in a methodical manner. Testing of code was required to uncover the problem, which was that the list of student records was not updated in time before being called by the UI. 
    This occurred when the relevant listener was activated upon the changing/adding of a Session to the Taskmaster. Fixing this issue required significant modifications to how the representation of the `StudentRecordList` was handled by the `Model` class.

* **Bug Fix**: [Block duplicate `Session` names](https://github.com/AY2021S1-CS2103-F09-1/tp/pull/164)

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2021s1.github.io/tp-dashboard/#breakdown=true&search=&sort=groupTitle&sortWithin=title&since=2020-08-14&timeframe=commit&mergegroup=&groupSelect=groupByRepos&checkedFileTypes=docs~functional-code~test-code~other&tabOpen=true&tabType=authorship&tabAuthor=jayarengam&tabRepo=AY2021S1-CS2103-F09-1%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code~other)

* **Enhancements to existing features**:
  * Updated the GUI color scheme (Pull requests [\#33](), [\#34]())
  * Wrote additional tests for existing features to increase coverage from 88% to 92% (Pull requests [\#36](), [\#38]())

* **Documentation**:
  * User Guide:
    * Added documentation for above implemented features.
    * Did cosmetic tweaks to existing documentation of features `clear`, `exit`: [\#74]()
  * Developer Guide:
    * Added implementation details for storage of `SessionList`
    * Updated Use Cases for new features
    * Added non-functional requirements
  * Maintained project document

* **Community**:
  * PRs reviewed (with non-trivial review comments): [\#27](https://github.com/AY2021S1-CS2103-F09-1/tp/pull/27)
  * [Other PRs reviewed](https://github.com/AY2021S1-CS2103-F09-1/tp/pulls?q=is%3Apr+is%3Aclosed+reviewed-by%3Ajayarengam+)
