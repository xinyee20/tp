# Serenity - User Guide

![Serenity Logo](images/logo.png)

By: `Team Serenity` Since: `Aug 2020`

![Serenity Logo]()

1. [Introduction](#introduction)

2. [Quick Start](#quick-start)

3. [About](#about)

4. [Features](#features)

   4.1. [Setup](#setup)
   
     * [Setup classes at the start of a semester: `importCsv`](#setup-classes-at-the-start-of-a-semester-importcsv)
   
   4.2. [Attendance Taking](#attendance-taking)
  
     * [Mark attendance for a every student: `markAll`](#mark-attendance-for-every-student-markall)
     
     * [Mark attendance for a single student: `mark`](#mark-attendance-for-a-single-student-mark)
     
     * [Flag attendance for a single student: `flag`](#flag-attendance-for-a-single-student-flag)
     
     * [View attendance for a each class: `attendance`](#view-attendance-for-each-class-attendance)
     
     * [Exporting of attendance to CSV: `exportAtt`](#exporting-of-attendance-to-csv-exportatt)
     
   4.3. [Class Participation](#class-participation)
   
     * [Awarding class participation marks: `award`](#awarding-class-participation-marks-award)
     
     * [Viewing statistics of class participation `stats`](#viewing-statistics-of-class-participation-stats)
     
     * [Exporting of class participation grades to CSV: `exportCp`](#exporting-of-class-participation-grades-to-csv-exportcp)
     
   4.4 [Addressing Questions](#addressing-questions)
   
     * [Adding a question: `addQn`](#adding-a-question-addqn)
     
     * [Viewing all questions: `list`](#viewing-all-questions-list)
   
     * [Deleting a question: `deleteQn`](#deleting-a-question-deleteqn)
     
     * [Marking a question as answered: `ansQn`](#marking-a-question-as-answered-ansqn)
     
5. [FAQ](#faq)

6. [Command Summary](#command-summary)

## Introduction

Welcome to the User Guide of **Serenity**!

Are you a tutor for CS2101, but annoyed at keeping track of attendance, questions and class participation grades on different 
excel sheets for different classes? Fret not, our application ,**Serenity**, will help keep you sane when doing the necessary 
administrative work. **Serenity** is a desktop application that helps CS2101 tutors **manage their classes**. This  
application is optimized for use through a *Command Line Interface(CLI)*, meaning that you operate the application by 
typing commands into a command box.

This user guide serves to provide you with an in-depth documentation on how to set up and use our application. with that said
let's get [started](#quick-start)!

## Quick start

1. Ensure you have Java `11` or above installed in your Computer.

1. Download the latest `Serenity.jar` from [here]().

1. Copy the file to the folder you want to use as the _home folder_ for your AddressBook.

1. Double-click the file to start the app. The GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui]()

1. Type the command in the command box and press Enter to execute it. 

1. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## About

about goes here

--------------------------------------------------------------------------------------------------------------------

## Features

### <ins>Setup</ins>

#### Setup classes at the start of a semester: `importCsv`

### <ins>Attendance Taking</ins>

#### Mark attendance for every student: `markAll`

#### Mark attendance for a single student: `mark`

#### Flag attendance for a single student: `flag`

#### View attendance for each class: `attendance`

#### Exporting of attendance to CSV: `exportAtt`

### <ins>Class Participation</ins>

This feature allows you to manage the class participation grades for each student in your classes.

---

#### Awarding class participation marks: `award`

you can use this command to award class participation marks to a student from a specified tutorial group and tutorial 
you wish to grade.

##### Format:

`award NAME TUTORIAL_GROUP TUTORIAL_NUMBER MARKS`

##### Example:

If you wish to award a student participation marks, follow the steps below to learn how. In this example you will learn
how to award a student ,`Ryan`, from tutorial group `G04`, a class participation score of `4` for tutorial `3-1`.

Awarding class participation:

1. Type `award Ryan G04 3-1 4`

2. Press `enter` to execute the command 

outcome:



---

#### Viewing statistics of class participation: `stats`

you can use this command to view the average score for each student for a specific tutorial group and tutorial.

##### Format:

`stats TUTORIAL_GROUP LESSON_NUMBER`

##### Example:

If you wish to view the average participation marks for tutorial group `GO9` and tutorial `7-2`, follow the steps below 
to learn how. 

Viewing the average class participation mark

1. Type `stats G09 7-2`

2. Press `enter` to execute the command 

outcome:


---

#### Exporting of class participation grades to CSV: `exportCp`

You can use this command to export the class participation marks of a specified tutorial group into a CSV file.

##### Format:

`exportcp TUTORIAL_GROUP`

##### Example:

Assuming you need to collate the class participation marks for tutorial group `GO7` into a CSV file for grading on a 
different platform (i.e LuimiNUS). You can do the following steps

exporting class participation marks:

1. Type `exportcp G07`

2. Press `enter` to execute the command 

outcome:


---

### <ins>Addressing Questions</ins>

#### Adding a question: `addQn`

#### Viewing all questions: `list`

#### Deleting a question: `deleteQn`

#### Marking a question as answered: `ansQn` 

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous AddressBook home folder.

--------------------------------------------------------------------------------------------------------------------

## Command summary

Action | Format, Examples
--------|------------------
**Add** | `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…​` <br> e.g., `add n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 t/friend t/colleague`

