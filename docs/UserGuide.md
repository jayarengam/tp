---
layout: page
title: User Guide
---

TAskmaster is a **desktop app for managing students, optimised for use via a Command Line Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI). If you are a CS Teaching Assistant who can type fast, TAskmaster can help you track your students' attendance and class participation faster than traditional GUI apps.

## Contents:
- [Quick Start](#quick-start "Go to Quick Start")
    - [Installation](#installation "Go to Installation")
    - [Usage](#usage "Go to Usage")
- [UI](#ui "Go to UI")
- [Commands](#commands "Go to Commands")
    - [Adding a student: `add-student`](#adding-a-student-add-student "Go to Adding a student")
    - [Listing all students: `list-students`](#listing-all-students-list-students "Go to Listing all students")
    - [Finding students by name: `find-students`](#finding-students-by-name-find-students "Go to Finding students by name")
    - [Editing a student: `edit-student`](#editing-a-student-edit-student "Go to Editing a student")
    - [Deleting a student: `delete-student`](#deleting-a-student-delete-student "Go to Deleting a student")
    - [Adding a session: `add-session`](#adding-a-session-add-session "Go to Adding a session")
    - [Delete a session: `delete-session`](#delete-a-session-delete-session "Go to Deleting a session")
    - [Changing the current session: `goto`](#changing-the-current-session-goto "Go to Changing the current session")
    - [Marking a student's attendance: `mark`](#marking-a-students-attendance-mark "Go to Marking a student's attendance")
    - [Marking all students' attendance: `mark all`](#marking-all-students-attendance-mark-all "Go to Marking all students' attendance")
    - [Scoring students' participation: `score`](#scoring-students-participation-score "Go to Scoring students' participation mark")
    - [View lowest scoring students: `lowest-score`](#View-lowest-scoring-students-lowest-score "Go to View lowest scoring students")
    - [Clear all students: `clear`](#clearing-all-entries-clear "Go to Clearing all entries")
    - [Exit the program: `exit`](#exiting-the-program-exit "Go to Exiting the program")
- [Command Summary](#command-summary "Go to Command Summary")
- [Storage](#storage "Go to Storage")
- [FAQ](#frequently-asked-questions-faq "Go to Frequently Asked Questions (FAQ)")

## Quick Start

### Installation
1. Ensure you have Java 11 or later installed.
2. Download the jar file from the project website.
3. Copy the file to the folder you want to use as the home folder for the application.
4. Double-click the file to start the app. A GUI should appear, with the field bar to input commands. The list of commands are available below.

### Usage
1. Add the students that you are currently teaching into TAskmaster using the `add-student` command.
    * You can use the `list-students`, `edit-student`, `find-students` and `delete-student` commands to read and modify your student list.
2. Create a new session that represents a tutorial, lab or recitation session using the `add-session` command.
    * This session will read your student list and create a list of corresponding student records belonging to that session
    * Each student in the student list will be represented by a student record
    * This list of records, once created, will be **independent of the student list**. Any modifications to the student list after a session is created **will not** affect the student records in that session.
    * You can use the `delete-session` command to delete a session from the session list.
3. Mark your students' attendance and award them class participation marks with the `mark` and `score` commands respectively.
    * Note that you will not be allowed to use these commands outside of a session.

## UI

### Accessing the student lists
Click on the button titled "Students".
![UiShowcase](images/UiShowcase.png)

### Accessing a session
Click on the button with the name of the Session.
![Ui](images/Ui.png)
The number below the attendance of the student is their current class participation score for the selected session.


## Commands
> Command format:
> - Words in `UPPER_CASE` are parameters supplied by the user
> - Items in square brackets are optional
> - Items with ellipses (`...`) after them can be used multiple times including zero times
> - Parameters can be in any order

### Adding a student: `add-student`
Adds a student into the student list.
```
add-student n/NAME u/TELEGRAM e/EMAIL i/NUSNETID [t/TAG]...
```
- The `NAME` must be non-empty.
- The `TELEGRAM` handle must be a valid handle (comprising only alphanumeric characters and underscores with length between 5 and 32 characters inclusive).
- The `EMAIL` and `NUSNETID` must be in a valid format.

Example usage:
```
add-student n/John Tan u/johntan98 e/johntan98@gmail.com i/e0012345 t/tardy
```

### Listing all students: `list-students`
Shows a list of all students in the student list.
```
list-students
```

### Finding students by name: `find-students`
Finds students whose name contains any of the given keywords.
```
find-students KEYWORD [MORE_KEYWORDS]
```
- The search is case-insensitive (e.g. `hans` will match `Hans`).
- The order of the keywords does not matter (e.g. `Hans Bo` will match `Bo Hans`).
- Only the name is searched.
- Only full names will be matched (e.g. `han` will NOT match `Hans`).
- Students matching at least one keyword will be returned (e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`)

Example usages:
```
// Returns 'john' and 'John Doe'
find-students John

// Returns 'Alex Yeoh', 'David Li'
find-students alex david
```

### Editing a student: `edit-student`
Edits an existing student in the student list.
```
edit-student INDEX [n/NAME] [u/TELEGRAM] [e/EMAIL] [i/NUSNETID] [t/TAG]...
```
- Edits the student at the specified `INDEX` number shown in the displayed student list. 
- The `INDEX` **must be a positive integer** that exists in said list.
- At least one of the optional fields must be provided.
- Existing values will be updated to the input values.
- When editing tags, the existing tags of the person will be removed i.e adding of tags is not cumulative.
- You can remove all the person’s tags by typing `t/` without specifying any tags after it.

Example usages:
```
// Edit the telegram and email of the first person
edit-student 1 u/johntan98 e/johntan98@gmail.com

// Edit the name of the second person and clear all existing tags
edit-student 2 n/Rachel Lee t/
```

### Deleting a student: `delete-student`
Deletes the specified student from the student list.
```
delete-student INDEX
```
- Deletes the student at the specified `INDEX` number shown in the displayed student list.
- The `INDEX` **must be a positive integer** that exists in said list.

Example usage:
```
delete-student 3
```

### Adding a session: `add-session`
Adds a session into the session list.
```
add-session s/SESSION_NAME dt/SESSION_DATE_TIME
```
- The `SESSION_DATE_TIME` must be a valid date of the format `dd-MM-yyyy HHmm`.
- Duplicate session names are not allowed.

Example usage:
```
add-session s/CS2103 Tutorial 9 dt/23-10-2020 0900
```

### Deleting a session: `delete-session`
Deletes the specified session from the session list.
```
delete-session s/SESSION_NAME
```
- Deletes the session with the specified `SESSION_NAME` from the displayed session list.
- A session with `SESSION_NAME` must exist in said list.

Example usage:
```
delete-session s/CS2103 Tutorial 9
```

### Changing the current session: `goto`
Changes the current session to the session with the specified name.
```
goto s/SESSION_NAME
```
- The `SESSION_NAME` must belong to one of the existing sessions in the session list.

Example usage:
```
goto s/CS2103 Tutorial 9
```

### Marking a student's attendance: `mark`
Marks the attendance of the specified student from the student list.
```
mark INDEX a/ATTENDANCE_TYPE
```
- Marks the attendance at the specified `INDEX` number shown in the displayed student record list.
- The `INDEX` **must be a positive integer** that exists in said list.
- The `ATTENDANCE_TYPE` must either be `present` or `absent`.

Example Usage:
```
mark 1 a/absent
```

### Marking all students' attendance: `mark all`
Marks the attendance of all students in the student list.
```
mark all a/ATTENDANCE_TYPE
```
- Marks the attendances of all students shown in the displayed student record list.
- The `ATTENDANCE_TYPE` must either be `present` or `absent`.

Example Usage:
```
// Mark all students as present except for student at index 2 who is absent
mark all a/present
mark 2 a/absent
```

### Scoring students' participation: `score`
Scores the participation of student(s) in the session.
```
score INDEX cp/SCORE
```
- Scores the participation of the student associated with the specified `INDEX` number shown in the displayed student record list.
- The `INDEX` **must be a positive integer** that exists in said list.
- The `SCORE` **must be a non-negative number** between 0 and 10, inclusive. 
Taskmaster supports detailed score up to 2 decimal places.
For scores with more than 2 decimal places, the score will be rounded to the nearest 2 decimal places.  
Taskmaster supports scoring students' participation score even though the student is absent, to allow for
module-specific instructions (e.g. a module coordinator instructs the TA to give
a participation mark of 6 if a valid MC is given)

To mark all students **who are present**, you can replace the `INDEX` by the keyword `all`.

Example Usage:
```
score 1 cp/5
score 3 cp/6.9
score 4 cp/4.21
score all cp/10
score all cp/2.94
```

### View lowest scoring students: `lowest-score`
Displays all students with the lowest score in the current session.
```
lowest-score
```
- Lists the records of all students in the current session with the lowest score.
- Will show no students if the student record list is empty.

### Get random student: `random-student`
Displays a random student from the current session
```
random-student
```
- Lists a random student from the current session.
- Does not work if the session has no students.

### Clearing all entries: `clear`
Clears all saved data in taskmaster (both students and sessions).
```
clear
```
- This will delete all students and all sessions (including student record data).

### Exiting the program: `exit`
Exits the program.
```
exit
```
- Data is automatically saved.

## Command Summary

| Action            | Format, Examples                                                                                              |
|-------------------|---------------------------------------------------------------------------------------------------------------|
| Add student       | ```add-student n/NAME u/TELEGRAM e/EMAIL i/NUSNETID [t/TAG]``` <br> e.g., ```add-student n/John Tan u/johntan98```<br>```e/johntan98@gmail.com i/e0012345 t/tardy```  |
| List students     | ```list-students```                                                                                               |
| Find students     | ```find-students KEYWORD [MORE_KEYWORDS]``` <br> e.g., ```find-students alex david```                                      |
| Edit student      | ```edit-student INDEX [n/NAME] [u/TELEGRAM] [e/EMAIL] [i/NUSNETID] [t/TAG]...```<br> e.g., ```edit-student 1 u/johntan98 e/johntan98@gmail.com```                                                           |
| Delete student    | ```delete-student INDEX``` <br> e.g., ```delete-student 3```                                                             |
| Add session       | ```add-session s/SESSION_NAME dt/SESSION_DATE_TIME``` <br> e.g., ```add-session s/CS2103 Tutorial 9 dt/23-10-2020 0900```|
| Change session    | ```goto s/SESSION_NAME``` <br> e.g., ```goto s/CS2103 Tutorial 9```
| Delete session    | ```delete-session s/SESSION_NAME``` <br> e.g., ```delete-session s/CS2103 Tutorial 9```
| Mark              | ```mark INDEX a/ATTENDANCE_TYPE``` <br> e.g., `mark 1 a/absent`                                             |
| Mark all          | ```mark all a/ATTENDANCE_TYPE``` <br> e.g., `mark all a/present`
| Score             | ```score INDEX cp/SCORE``` <br> e.g., `score 1 cp/5`                                             |
| Score all         | ```score all cp/SCORE``` <br> e.g., `score all cp/10`
| Lowest score      | ```lowest-score```      |
| Random student    | ```random-student```     |
| Clear             | ```clear```                                                                                              |
| Exit              | ```exit```                                                                                              |

## Storage
The TAskmaster stores the running list of students and sessions automatically to JSON files when they are updated.

**Student data** is stored in `data/taskmaster.json`
**Session data** is stored in `data/session_list.json`

Avoid modifying the files as this may cause the program to read the input as invalid on startup and load with an
empty student list without any sessions.

## Frequently Asked Questions (FAQ)
How to download java? [Here](https://lmgtfy.com/?q=how+to+download+java)
