# Serenity - User Guide

![Serenity Logo](images/logo.png)

By: `Team Serenity` Since: `Aug 2020`

1. [Introduction](#introduction)

2. [Quick Start](#quick-start)

3. [About](#about)
    
    3.1. [Structure of this document](#structure-of-this-document)
    
    3.2. [Reading this document](#reading-this-document)
    
    &nbsp;&nbsp;&nbsp;&nbsp;3.2.1. [Terminology related to the GUI](#terminology-related-to-the-GUI)
        
    &nbsp;&nbsp;&nbsp;&nbsp;3.2.2. [General symbols and syntax](#general-symbols-and-syntax)
    
    &nbsp;&nbsp;&nbsp;&nbsp;3.2.3. [Command syntax and usage](#command-syntax-and-usage)
    
    &nbsp;&nbsp;&nbsp;&nbsp;3.2.4. [Command format](#command-format)

4. [Features](#features)

   4.1. [Setup](#setup)

   &nbsp;&nbsp;&nbsp;&nbsp;4.1.1. [Add a new tutorial group from CSV file: `addgrp`](#add-a-new-tutorial-group-from-csv-file-addgrp)
   
   &nbsp;&nbsp;&nbsp;&nbsp;4.1.2. [Delete-an-existing-tutorial-group-delgrp]()

   4.2. [Attendance Taking](#attendance-taking)

     * [Mark attendance for every student: `markpresent all`](#mark-attendance-for-every-student-markpresent-all)

     * [Mark attendance for a single student: `markpresent`](#mark-attendance-for-a-single-student-mark)

     * [Flag attendance for a single student: `flag`](#flag-attendance-for-a-single-student-flag)

     * [View attendance for a each class: `attendance`](#view-attendance-for-each-class-attendance)

     * [Exporting of attendance to CSV: `exportAtt`](#exporting-of-attendance-to-csv-exportatt)

   4.3. [Class Participation](#class-participation)

     * [Awarding class participation marks: `award`](#awarding-class-participation-marks-award)

     * [Viewing statistics of class participation `stats`](#viewing-statistics-of-class-participation-stats)

     * [Exporting of class participation grades to CSV: `exportCp`](#exporting-of-class-participation-grades-to-csv-exportcp)

   4.4. [Question Addressing](#ins44-question-addressingins)
       
      4.4.1. [Adding a question: `addQn`](#441-add-a-new-question-addqn)

      4.4.2. [Deleting a question: `deleteQn`](#442-delete-an-existing-question-delqn)

   4.5. [Utility](#ins45-utilityins)
   
      4.5.2. [View an existing tutorial lesson: `viewlsn`](#452-view-an-existing-tutorial-lesson-viewlsn)

5. [FAQ](#faq)

6. [Command Summary](#command-summary)

--------------------------------------------------------------------------------------------------------------------

## 1. Introduction

Welcome to the User Guide of **Serenity**!

Are you a tutor teaching CS2101 in NUS? Do you struggle to keep track of students' attendance and class participation
on many different excel sheets for different lessons? Do you also tend to forget to address questions students asked
during lessons? Fret not, our application, **Serenity**, will help keep you sane when doing the necessary administrative
work. **Serenity** is a desktop application that helps CS2101 tutors manage their lessons. This application is optimized
for use through a Command Line Interface (CLI), meaning that you operate the application by typing commands into a
command box.

This user guide serves to provide you with an in-depth documentation on how to set up and use our application.
With that said, let's get [started](#quick-start)!

--------------------------------------------------------------------------------------------------------------------

## 2. Quick start

To get started with using **Serenity**, you can follow these steps:

1. Ensure you have Java `11` or above installed in your Computer.

2. Download the latest `Serenity.jar` from [here]().

3. Copy the file to the folder you want to use as the home folder for your **Serenity** application.

4. Double-click the file to start the app. The GUI similar to the below should appear in a few seconds.

   ![Ui](images/Ui.png)

5. Type the command in the command box and press Enter to execute it. 

6. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## 3. About

### 3.1. Structure of this document

### 3.2. Reading this document

#### 3.2.1. Terminology related to the GUI

#### 3.2.2. General symbols and syntax

The table below explains the general symbols and syntax used throughout the document.

Symbol/syntax | Meaning
--------|------------------
command | This is a command that the user can type into the command box for **Serenity** to execute.
ℹ | This indicates that the enclosed text is something to note.
💡 | This indicates that the enclosed text is a tip.
⚠ | This indicates that the enclosed text is a warning.

#### 3.2.3. Command syntax and usage

The table below explains some important technical terms. <br>

Technical term | Meaning
--------|------------------
Command | The instruction that the user types into the command box for Serenity to perform a specific task.
Command word | The first word of the command that tells Serenity which task should be performed.
Parameter | The word or phrase following the command word that provides further details of the task (if necessary).
Prefix | The letter that is placed at the start of each parameter to distinguish one parameter from another.

#### 3.2.4. Command format
You can use commands to tell Serenity to perform a specific task. <br>

Format:
`COMMAND_WORD prefix1/PARAMETER1 [prefix2/PARAMETER2]`

> ℹ **Notes about the command format:**
>* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>e.g. in `viewgrp grp/GROUP`, `GROUP` is a parameter which can be used as `viewgrp grp/G04`
>* Parameters have to be in the specified order<br>e.g. if the command specifies `grp/GROUP lsn/LESSON`, `lsn/LESSON grp/GROUP` is not acceptable.

**Example:**
If you need to add a new tutorial lesson, you can type the following command.
`addlsn grp/GROUP lsn/LESSON`

**Analysis:**
Command - Add a tutorial lesson
Command Word - `addlsn`
Parameter - `GROUP`, `LESSON`
Prefix - `grp/`, `lsn/`

--------------------------------------------------------------------------------------------------------------------

## 4. Features

This section contains all the information you need to know about the features of **Serenity**.
To use each feature or sub-feature, you will need to enter the command into the _Command Box_.

### 4.1. Setup

It is the start of the semester! Brand new classes, brand new students and a whole new experience.
Follow the instructions below to set up your new classes, and watch **Serenity** do the magic for you.

#### 4.1.1. Add a new tutorial group from CSV file: `addgrp`

You can use this command to add a new tutorial group automatically from CSV data.

> ℹ **Notes about the `addgrp` command:**
> * The CSV file should store a list of students.
> * The format of the CSV file is the same as the CSV file that you may download from LUMINUS.
> * To obtain PATH_TO_CSV, either:<br>
>    ○ Get the location of the CSV file in your computer.<br>
>      e.g. `addgrp grp/G04 path/C:\Users\serene\CS2101_G04.csv`<br>
>    ○ Copy and paste the CSV file into the same folder as your **Serenity** application, then type the name of the CSV file.<br>
>      e.g. `addgrp grp/G04 path/CS2101_G04.csv`

**Format:**
`addgrp grp/GROUP path/PATH_TO_CSV`

**Example:**
If you are adding a new tutorial group to prepare for a new semester, follow these steps below.
You will learn how to add a tutorial group named **G04** where its CSV data is stored at **C:\Users\serene\CS2101_G04.csv**.

Adding a new tutorial group:
1. Type `addgrp grp/G04 path/C:\Users\serene\CS2101_G04.csv` into the _Command Box_.
2. Press `Enter` to execute.

Outcome:
1. The _Result Display_ will show a success message.
2. **Serenity** will switch to tutorial group **G04** page.
3. You can now see the list of students and a default list of tutorial lessons.

3. You can now see the list of students and a default list of tutorial lessons.

#### 4.1.2. Delete an existing tutorial group: `delgrp`

You can use this command to delete an existing tutorial group.

**Format:**
`delgrp grp/GROUP`

**Example:**
If you accidentally added the wrong tutorial group **G04** and decided to remove the tutorial group,
you can perform the steps below.

Deleting an existing tutorial group:
1. Type `delgrp grp/G04` into the _Command Box_.
2. Press `Enter` to execute.

Outcome:
1. The _Result Display_ will show a success message.
2. **Serenity** will exit tutorial group **G04** page.
3. You can now see that **G04** no longer exists.

#### 4.1.3. Add a new tutorial lesson: `addlsn`

You can use this command to add a new tutorial lesson for a specified tutorial group.

**Format:**
`addlsn grp/GROUP lsn/LESSON`

**Example:**
If you want to create a new lesson **2-2** for tutorial group **G04**, you can perform the steps below.

Adding a lesson to a tutorial group:
1. Type `addlsn grp/G04 lsn/2-2` into the _Command Box_.
2. Press `Enter` to execute.

Outcome:
1. The _Result Display_ will show a success message.
2. **Serenity** will display tutorial lesson **2-2** page.
3. You can now see that tutorial lesson **2-2** has been added to tutorial group **G04**.

#### 4.1.4. Delete an existing tutorial lesson: `dellsn`

You can use this command to delete an existing lesson from a specified tutorial group.

**Format:**
`dellsn grp/GROUP lsn/LESSON`

**Example:**
If you accidentally added a lesson **2-3** for tutorial group **G04** and decided to remove it,
you can perform the steps below.

Deleting an existing lesson from a tutorial group:
1. Type `dellsn grp/G04 lsn/2-3` into the _Command Box_.
2. Press `Enter` to execute.

Outcome:
1. The _Result Display_ will show a success message. 
2. **Serenity** will exit tutorial lesson **2-3** page and enter tutorial group **G04** page.
3. You can now see that tutorial lesson **2-3** no longer exists.

#### 4.1.5. Add a new student: `addstudent`

You can use this command to add a new student to a tutorial group.

**Format:**
`addstudent grp/GROUP name/NAME id/STUDENT_ID`

**Example:**
A new student named `Aaron Tan` with student ID `e0123456` entered your tutorial group, and you would like to add him to tutorial group `G04`.

Adding a new student to a tutorial group:
1. Type `addstudent grp/G04 name/Aaron Tan id/e0123456` into the _Command Box_.
2. Press `Enter` to execute.

Outcome:
1. The _Result Display_ will show a success message.
2. `Aaron Tan` will be added to the list of students in tutorial group **G04**.

#### 4.1.6. Delete an existing student: `delstudent`

You can use this command to delete an existing student from a tutorial group.

**Format:**
`delstudent grp/GROUP name/NAME id/STUDENT_ID`

**Example:**
A student named `Aaron Tan` with student ID `e0123456` has left your tutorial group,
and you would like to remove him from the tutorial group `G04`.

Deleting an existing student from a tutorial group:
1. Type `delstudent grp/G04 name/Aaron Tan id/e0123456` into the _Command Box_.
2. Press `Enter` to execute.

Outcome:
1. The _Result Display_ will show a success message.
2. `Aaron Tan` will no longer be in the list of students in tutorial group **G04**.

### 4.2. Attendance Taking

#### 4.2.1. Mark a student as present: `markpresent`

You can use this command to mark a specific student as present for a tutorial lesson.

**Format:**
`markpresent name/NAME id/STUDENT_ID`

**Example:**
There is a student named `Aaron Tan` with Student ID of `e0123456` who is present for the tutorial lesson `1-2` of group `G04`. 

Marking a student as present for a tutorial lesson:
1. Navigate to view group G04 lesson 1-2 via ![`viewlsn`](link to viewlsn command) command.
2. Type `markpresent name/Aaron Tan id/e0123456` into the _Command Box_.
3. Press `Enter` to execute.

Outcome:
1. The _Result Display_ will show a success message.
2. You can now see that his attendance has been updated on the _Student Information Panel_.

![Figure X. Outcome of a successful mark a student present command](images/ui/markpresent_student.png)
_Figure X. Outcome of a successful mark a student present command_

#### 4.2.2. Mark a student as absent: `markabsent`

You can use this command to mark a specific student as absent for a tutorial lesson.

**Format:**
`markabsent name/NAME id/STUDENT_ID`

**Example:**
There is a student `Aaron Tan` with Student ID `e0123456` from group `G04` who is absent for your tutorial lesson `1-2`. 

Marking a student as absent for a tutorial lesson:
1. Navigate to view group G04 lesson 1-2 via ![`viewlsn`](link to viewlsn command) command.
2. Type `markabsent name/Aaron Tan id/e0123456` into the _Command Box_.
3. Press `Enter` to execute.

Outcome:
1. The _Result Display_ will show a success message.
2. You can now see that his attendance has been updated on the _Student Information Panel_.

![Figure X. Outcome of a successful mark a student absent command](images/ui/markabsent_student.png)
_Figure X. Outcome of a successful mark a student absent command_

#### 4.2.3. Mark attendance for all students: `markpresent all`

You can use this command to mark all students in a tutorial group as present for a tutorial lesson.

> 💡 **Tip for the `markpresent all` command:**
> You can conveniently mark all students as present at once in the beginning of the tutorial lesson,
> then mark some students as absent afterwards. You can then start your tutorial lesson without much hassle.

**Format:**
`markpresent all`

**Example:**
All students in group `G04` are present for the tutorial lesson `1-2`. 

Marking all students in a tutorial group as present for a tutorial lesson:
1. Navigate to view group `G04` lesson `1-2` via ![`viewlsn`](link to viewlsn command) command.
2. Type `markpresent all` into the _Command Box_.
3. Press `Enter` to execute.

Outcome:
1. The _Result Display_ will show a success message.
2. You can now see that all the students' attendance have been updated on the *Student Information Panel*.

![Figure X. Outcome of a successful mark attendance for all students command](images/ui/markpresent_all.png)
_Figure X. Outcome of a successful mark attendance for all students command_

#### 4.2.4. Flag attendance of a student: `flag`

You can use this command to flag attendance of a specific student so you will be reminded to check the student's
attendance at the end of class.

**Format:**
`flag grp/GROUP lsn/LESSON name/NAME id/STUDENT_ID`

**Example:**
A student `Aaron Tan` with Student ID `e0123456` from another tutorial group decided to join your tutorial lesson `1-2`.
You have to check with his tutor regarding his attendance after your tutorial lesson. 

Flagging a student's attendance for a tutorial lesson:
1. Type `flag grp/G04 lsn/1-2 name/Aaron Tan id/e0123456` into the _Command Box_.
2. Press `Enter` to execute.

Outcome:
1. The _Result Display_ will show a success message.
2. His attendance will now be flagged for future follow-up actions.

#### 4.2.5. Export attendance as CSV file: `exportatt`

You can use this command to export the attendance sheet of a specific tutorial group as a CSV file.

> 💡 **Tip for the `exportatt` command:**
> You can conveniently obtain the soft copy version of the attendance sheet for
> attendance list submission as part of NUS Centre for English Language Communication requirements.

> ℹ **Notes about the `exportatt` command:**
> The attendance sheet will be saved as a CSV file named after the tutorial group.
> The file will be saved at the same folder as your **Serenity** application.

**Format**:
`exportatt grp/GROUP`

**Example**:
You want to export the attendance sheet of tutorial group **G04**.

Exporting attendance sheet of a tutorial group:
1. Type `exportatt grp/G04` into the _Command Box_.
2. Press `Enter` to execute.

Outcome:
1. The _Result Display_ will show a success message.
2. The attendance sheet of tutorial group **G04** will be saved as `G04.csv`. You can find the CSV file at the location
where your **Serenity** application is located.

### 4.3. Participation Marking

Need to keep track of all your students' participation during lessons quickly? This feature allows you to key in a 
student's participation score easily with a scale for reference.

#### 4.3.1. Award participation score for a student: `addscore`
You can use this command to add the participation score for a specific student in a tutorial lesson.

With a scale from 1 to 5:
Score | 1 | 2 | 3 | 4 | 5 | 
------|---|---|---|---|---
**Remark** |Very Poor| Poor| Sufficient|Good|Commendable

**Format:**
`addscore name/NAME id/STUDENT_ID score/SCORE`

**Example:**
In tutorial lesson **1-2**, you felt that your student `Aaron Tan` with Student ID `e0123456` from tutorial group **G04**
participated sufficiently in class but he could have given more insightful responses. You decided to award him a score
 of `3` for his participation.

Awarding a student participation score in a tutorial lesson:
1. Navigate to view group G04 lesson 1-2 via ![`viewlsn`](link to viewlsn command) command.
2. Type `addscore name/Aaron Tan id/e0123456 score/3` into the _Command Box_.
3. Press `Enter` to execute.

Outcome:
1. The _Result Display_ will show a success message.
2. You can now see that his score has been updated on the _Student Information Panel_.

![Figure X. Outcome of a successful add score for 1 student command](images/ui/addscore_student.png)
_Figure X. Outcome of a successful add score for a student command_

#### 4.3.2. Export participation scores as CSV file: `exportscore`

You can use this command to export the participation scores of a specified tutorial group as a CSV file.

**Format:**
`exportscore grp/GROUP`

**Example:**
You may need to collate the class participation marks for tutorial group `GO7` into a CSV file for grading on a 
different platform (i.e LumiNUS). You can do the following steps.

Exporting participation scores:
1. Type `exportscore grp/G07` into the _Command Box_.
2. Press `Enter` to execute.

Outcome:
1. The _Result Display_ will show a success message.
2. The file will be generated.

### 4.4. Question Addressing

Need to keep track of all your students' questions during lessons quickly? This feature allows you to key in questions
easily for future reference.

#### 4.4.1. Add a new question: `addqn`

You can use this command to add a question that was asked in a lesson, so that you can address the question at the end
of the lesson.

**Format:**
`addqn qn/QUESTION`

**Example:**
A student asked, "What is the deadline for the report?" in a tutorial lesson.
As there were some changes made to the deadline, you want to note this question down, check the new deadline and
address the question after the tutorial lesson.

Adding a question:
1. Navigate to view group `G04` lesson `1-2` via [`viewlsn`](#452-view-an-existing-tutorial-lesson-viewlsn) command.
2. Type `addqn qn/What is the deadline for the report?` into the _Command Box_.
3. Press `Enter` to execute.

Outcome:
1. The _Result Display_ will show a success message.
2. The question will be added to the list of questions.

#### 4.4.2. Delete an existing question: `delqn`

You can use this command to delete a question that was asked in a tutorial lesson, after addressing the question at the
end of the lesson.

**Format:**
`delqn INDEX`

**Example:**
You addressed one of the questions at the end of a tutorial lesson and you want to delete the question from the
list of questions.

Deleting a question:
1. Navigate to view group `G04` lesson `1-2` via [`viewlsn`](#452-view-an-existing-tutorial-lesson-viewlsn) command.
2. Type `delqn 1` into the _Command Box_.
3. Press `Enter` to execute.

Outcome:
1. The _Result Display_ will show a success message.
2. The question will be deleted from the list of questions.

### 4.5 Utility

#### 4.5.1. View an existing tutorial group: `viewgrp`
You can use this command to view the list of students and tutorial lessons of an existing tutorial group.
You will also be able to execute tutorial group-related commands.

**Format:**
`viewgrp grp/GROUP`

**Example:**
Suppose you would like to view the details for tutorial group `G04`. You can perform the following steps.

Viewing a tutorial group:
1. Type `viewgrp grp/G04` into the _Command Box_.
2. Press `Enter` to execute.

Outcome:
1. The _Result Display_ will show a success message.
2. The _Data Display_ will update and display the information for tutorial group `G04`.

![Figure X. Outcome of a successful view group command](reference)

#### 4.5.2. View an existing tutorial lesson: `viewlsn`

You can use this command to view the attendance and participation scores of an existing
tutorial group. You will also be able to execute tutorial lesson-related commands.

**Format:**
`viewlsn grp/GROUP lsn/LESSON`

**Example:**
Suppose you would like to view the details for tutorial group `G04`'s tutorial lesson `1-2`.
You can perform the following steps.

Viewing a tutorial lesson:
1. Type `viewlsn grp/G04 lsn/1-2` into the _Command Box_.
2. Press `Enter` to execute.

Outcome:
1. The _Result Display_ will show a success message.
2. The _Data Display_ will update and display the students' information for tutorial group `G04`'s lesson `1-2`.

![Figure X. Outcome of a successful `viewlsn` command](#)

--------------------------------------------------------------------------------------------------------------------

## 5. FAQ
**Q:** How many tutorial groups can I manage in **Serenity**?
**A:** You can manage more than 1 tutorial groups in **Serenity**.

**Q:** How do I transfer my data to another computer?
**A:** By default, **Serenity** saves all your data in a folder named `data`. This folder can be found in the
home folder for your **Serenity** application. You can copy and transfer the data folder into the home folder of
your **Serenity** application on your other computer. **Serenity** will automatically load your data upon launching.

--------------------------------------------------------------------------------------------------------------------

## 6. Command summary

### 6.1. Setup Commands

Command | Example
------------ | -------------
**Add tutorial group** <br>`addgrp GROUP PATH_TO_CSV`| addgrp grp/G04 path/C:\Users\serene\CS2101_G04.csv
**Delete tutorial group** <br> `delgrp GROUP`| delgrp grp/G04
**Add tutorial lesson** <br> `addlsn GROUP LESSON` | addlsn grp/G04 lsn/2-1
**Delete tutorial lesson** <br> `dellsn GROUP LESSON` | dellsn grp/G04 lsn/2-1
**Add student** <br> `addstudent GROUP NAME STUDENT_NUMBER` | addstudent grp/G04 name/Ryan id/e1234567
**Delete student** <br> `delstudent GROUP NAME STUDENT_NUMBER` | delstudent grp/G04 name/Ryan id/e1234567

### 6.2. Attendance Taking Commands

Command | Example
------------ | -------------
**Mark attendance for 1 student** <br> `markatt NAME ID` | markatt name/Ryan id/e0123456
**Unmark attendance for 1 student** <br> `unmarkatt NAME ID` | unmarkatt name/Ryan id/e0123456
**Mark attendance for all students** <br> `markatt all` | markatt all

### 6.3. Participation Marking Commands

Command | Example
------------ | -------------
**Add participation score for 1 student** <br> `addscore MARK NAME ID` | addscore 2 name/Ryan id/e0123456

### 6.4. Question Addressing Commands

Command | Example
------------ | -------------
**Add question** <br> `addqn QUESTION` | addqn qn/What is the deadline for the report?
**Delete question** <br> `delqn INDEX` | delqn 5

### 6.5. Utility Commands

Command | Example
------------ | -------------
**View tutorial group** <br> `viewgrp GROUP` | viewgrp grp/G04
**View tutorial lesson** <br> `viewlsn GROUP LESSON` | viewlsn grp/G04 lsn/2-1