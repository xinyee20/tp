---
layout: page
title: Ryan's Project Portfolio Page
---

## Project: Serenity

Serenity is a desktop application that helps CS2101 tutors manage their tutorial groups and lessons. 
The user interacts with it using a CLI, and it has a GUI created with JavaFX. 
It is written in Java, and has about 25k LoC.

### About the project

My team of 4 software engineering students and I were tasked with enhancing a basic command line 
interface (CLI) desktop addressbook application for our Software Engineering project. We chose to 
morph it into a CS2101 tutor's lesson management application called Serenity. 

This enhanced application enables CS2101 tutors to:
 * Import and export student and lesson data,
 * Record students' attendance,
 * Grade students' class participation score, and 
 * Jot down questions asked by students directly without opening an external note-taking application.

### Summary of contributions

Given below are my contributions to the project.

* **New Feature 1**: Implemented UI data panels to display group, lesson and question data respectively 
and ability to view a tutorial group's detail

    * what it does: 
       * The data panels encapsulates the data stored in group and level as well as in question and displays 
       them in an organised manner for the user's viewing.
       
       * `viewgrp` - Allows user to view the details of the tutorial group (Lessons already conducted, 
       Students in the tutorial group)
    
    * Justification: This enhancement is essential when managing classes, as it allows the user to have a visual of 
    the information in each group, lesson, and question (i.e students in a tutorial group, attendance of students for a 
    specific lesson).
    
    * Highlights: The implementation was moderately challenging and time-consuming as it required understanding 
    of how JavaFX displays data as well as designing the UI to fit the demographic of our target audience.
    
* **New Feature 2**: Added the ability to add and delete lessons through commands 

    * What it does :
    
        * `addLsn` - Allows the users to add a new lesson into the tutorial group.
        
        * `delLsn` - Allows the user to delete a lesson if there was a mistake
    
    * Justification: This feature improves the product because it allows user to keep track of lessons that were conducted
    and prepare for future lessons to be conducted 
    
    * Highlights: Moderate amount of effort was done in as implementation was rather straightforward, faced minor issues with 
    displaying lesson data in the Ui but managed to do a workaround by implementing an `ObservableList` and `FilteredList` 
    meant for lesson data for a specified group

* **New Feature 3**: Improved upon base code of the following managers
    
    1. `StudentInfoManager`

    2. `GroupManager`
    
    3. `StudentManager`
    
    4. `LessonManager`
    
    * what it does: The respective managers allows users to directly manipulate the data within specific objects.
    
    * Justification: This enhancement is essential as it follows the Law of Demeter, minimizing coupling between
    classes. This improves the architecture of the model component while also improving the testability of our software. It
    Also abstracts out the implementation of methods related to the specific objects from the `ModelMManager` to the respective
    managers.

    * Highlights: The implementation was challenging and time-consuming as the team and, I needed to do a deep analysis of 
    design and its alternatives, the Model component, Logic component, as well as the Ui Component. We also needed to refactor
    our original architecture design, and it required careful coordination and communication to prevent major conflicts.

* **New Feature 4**: did the attendance and participation score table view UI for Serenity.
 
    * What it does: provides an overall visual view of the student's attendance and participation across all lessons for a single 
    tutorial group. 
    
    * Justification: This enhancement improves the product as it provides the user a preliminary view of student's information
    accross all classes before exporting the data into an Excel file
    
    * Highlights: This enhancement required some time to understand how to extract the necessary data to be displayed in the table,
    and understanding of TableView API to populate data in a table format.
    
    

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2021s1.github.io/tp-dashboard/#breakdown=true&search=&sort=groupTitle&sortWithin=title&since=2020-08-14&timeframe=commit&mergegroup=&groupSelect=groupByRepos&checkedFileTypes=docs~functional-code~test-code~other&tabOpen=true&tabType=authorship&tabAuthor=ryanlimjr&tabRepo=AY2021S1-CS2103T-W12-4%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code)

* **Project management**:
    * Brainstormed ideas to improve architecture design (use of individual mangers to handle the data instead of deep nesting)
    * Contributed to weekly team meetings regrading design details and implementation constraints and possible issues in design alternatives

* **Enhancements to existing features**:
    * Detected and fixed bugs for some features. ( Pull request 
    [#269](https://github.com/AY2021S1-CS2103T-W12-4/tp/pull/269), 
    [#271](https://github.com/AY2021S1-CS2103T-W12-4/tp/pull/271), 
    [#277](https://github.com/AY2021S1-CS2103T-W12-4/tp/pull/277), 
    [#300](https://github.com/AY2021S1-CS2103T-W12-4/tp/pull/300), 
    [#301](https://github.com/AY2021S1-CS2103T-W12-4/tp/pull/301), 
    [#319](https://github.com/AY2021S1-CS2103T-W12-4/tp/pull/319)) 
    * Wrote additional tests for existing features to increase test coverage. ( Pull request
    [#142](https://github.com/AY2021S1-CS2103T-W12-4/tp/pull/142), 
    [#145](https://github.com/AY2021S1-CS2103T-W12-4/tp/pull/145),
    [#146](https://github.com/AY2021S1-CS2103T-W12-4/tp/pull/146), 
    [#148](https://github.com/AY2021S1-CS2103T-W12-4/tp/pull/148),
    [#287](https://github.com/AY2021S1-CS2103T-W12-4/tp/pull/287),
    [#290](https://github.com/AY2021S1-CS2103T-W12-4/tp/pull/290),
    [#293](https://github.com/AY2021S1-CS2103T-W12-4/tp/pull/293))
    * Improve code quality for certain commands to adhere to coding principles. ( Pull request [#199](https://github.com/AY2021S1-CS2103T-W12-4/tp/pull/199))
    * Minor Improvements to UI [#272](https://github.com/AY2021S1-CS2103T-W12-4/tp/pull/272)
    
* **Documentation**:
    * User Guide:
        * Added documentation for the features `viewgrp`, `addlsn` and `dellsn`. ( Pull request [#119](https://github.com/AY2021S1-CS2103T-W12-4/tp/pull/119))
        * Addressed documentation issues after initial peer review. ( Pull request [#154](https://github.com/AY2021S1-CS2103T-W12-4/tp/pull/154))
    * Developer Guide:
        * Added implementation details for Lesson Manager. ( Pull request [#204](https://github.com/AY2021S1-CS2103T-W12-4/tp/pull/204))

* **Community**:
 * Reported bugs and suggestions for other teams in the class (examples: 
    [1](https://github.com/AY2021S1-CS2103T-T12-3/tp/issues/231),
    [2](https://github.com/AY2021S1-CS2103T-T12-3/tp/issues/230), 
    [3](https://github.com/AY2021S1-CS2103T-T12-3/tp/issues/229),
    [4](https://github.com/AY2021S1-CS2103T-T12-3/tp/issues/228))
