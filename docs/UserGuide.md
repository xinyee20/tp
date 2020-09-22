# Serenity - User Guide

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
     
     * [Marking a question as answered: `ansQn`](#-marking-a-question-as-answered-ansqn)
     
5. [FAQ](#faq)

6. [Command Summary](#command-summary)

## Introduction

Introduction goes here

--------------------------------------------------------------------------------------------------------------------

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

#### Awarding class participation marks: `award`

#### Viewing statistics of class participation: `stats`

#### Exporting of class participation grades to CSV: `exportCp`

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

