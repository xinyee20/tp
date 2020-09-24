# Serenity - User Guide

![Serenity Logo](images/logo.png)

By: `Team Serenity` Since: `Aug 2020`

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
     
     * [Viewing all questions: `questions`](#viewing-all-questions-list)
   
     * [Deleting a question: `deleteQn`](#deleting-a-question-deleteqn)
     
     * [Marking a question as answered: `ansQn`](#marking-a-question-as-answered-ansqn)
     
5. [FAQ](#faq)

6. [Command Summary](#command-summary)

## Introduction

Welcome to the User Guide of **Serenity**!

Are you a tutor for CS2101, but annoyed at keeping track of attendance, questions and class participation grades on different 
excel sheets for different classes? Fret not, our application, **Serenity**, will help keep you sane when doing the necessary 
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

**Set up classes at the start of a semester:** `importCsv`

### <ins>Attendance Taking</ins>

#### Mark attendance for every student: `markAll`

#### Mark attendance for a single student as absent: `mark`

#### Flag attendance for a single student: `flag`

#### View attendance for each class: `attendance`

#### Exporting of attendance to CSV: `exportAtt`


### <ins>Class Participation</ins>

#### Awarding class participation marks: `award`

#### Viewing statistics of class participation: `stats`

#### Exporting of class participation grades to CSV: `exportCp`


### <ins>Addressing Questions</ins>

#### Adding a question: `addQn`

Adds a question to the list of questions of a specified tutorial group.

##### Format:

`addQn TUTORIAL_GROUP LESSON_NUMBER QUESTION_DESCRIPTION`

##### Examples:
* `addQn G07 7-1 Could you elaborate on the marking scheme for OP1?`
* `addQn G07 7-1 What are the deadlines that students should take note of?`

Outcome:

---

#### Viewing all questions: `questions`

View a list of all the questions from a specified tutorial group.

##### Format:

`questions TUTORIAL_GROUP LESSON_NUMBER`

##### Examples:
* `questions G07 7-1`
* `questions G10 5-2`

Outcome:

---

#### Deleting a question: `deleteQn`

Deletes the specific question from the list of questions of a specified tutorial group.

##### Format:

`deleteQn TUTORIAL_GROUP LESSON_NUMBER INDEX`
* Deletes the question at the specified `INDEX`.
* The index refers to the index number shown in the displayed question list.
* The index **must be a positive integer** 1, 2, 3, …​

##### Examples:
* `deleteQn G07 7-1 1`
* `deleteQn G10 5-2 3`

Outcome:

#### Marking a question as answered: `ansQn` 

Marks the specified question from the list of questions of a specified tutorial group as answered.

##### Format:

`ansQn TUTORIAL_GROUP LESSON_NUMBER INDEX`
* Marks the question at the specified `INDEX` as answered.
* The index refers to the index number shown in the displayed question list.
* The index **must be a positive integer** 1, 2, 3, …​

##### Examples:
* `ansQn G07 7-1 1`
* `ansQn G10 5-2 3`

Outcome:

--------------------------------------------------------------------------------------------------------------------

## FAQ

--------------------------------------------------------------------------------------------------------------------

## Command summary

Action | Format, Examples
--------|------------------
