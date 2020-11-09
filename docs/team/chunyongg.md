---
layout: page
title: Chun Yong's Project Portfolio Page
---
## Project: Serenity

Serenity is a desktop application that helps CS2101 tutors manage their tutorial groups and lessons. 
The user interacts with it using a CLI, and it has a GUI created with JavaFX. 
It is written in Java, and has about 25k LoC.

### Summary of contributions

* **New Feature 1**: Implementation of initial `addgrp` functionality
  * What it does: It allows for the addition of a new tutorial group 
  * Justification: This enhancement greatly improves the user experience by enabling users to
  import data instead of manually entering them. For instance, instead of adding 10 students in 10 commands,
  it can now be done via a single command.
  * Highlights: This feature was implemented at the start of the project, and required a careful
  planning of the design patterns used so that we can accommodate future features easily,
  without overhauling our entire code structure.
  
* **New Feature 2**: Implementation of `GroupManager`, `LessonManager` and `StudentManager`
  * What it does: Encapsulate individual functionalities such as tutorial groups,
   tutorial lessons, students and their attendance records into individual managers.
  * Justification: This enhancement improves the architecture of the model component, and the 
    storing of the data. It abstracts out the implementation of methods 
    related to questions from the `ModelManager` to the individual managers and lessen deep nesting 
    of data.
  * Highlights: This enhancement requires an in-depth analysis of design alternatives, the Model,
    Logic and Ui components. Ensuring that `ModelManager` would be able to integrate smoothly 
    with the individual managers, and ensuring the GUI updates automatically whenever 
    data is updated also required careful design of the managers.
    
* **New Feature 3**: Added the ability to load and save data into a JSON file.
  * What it does: 
    * Save data after a command is executed into JSON files.
    * Load data from JSON files when the app is launched at start.
  * Justification: This feature improves the product significantly because it enables users 
  to save the data at the end of every tutorial lesson, 
  and continue updating them in the future if necessary.
  * Highlights: As each student has multiple attendance and participation records 
  (one for each lesson), the data can get large quickly with just a few students
   and a few lessons. It was challenging to ensure that the data is properly serialized
  into JSON files and required in-depth planning of the structure of Jackson-friendly objects.
  At app launch, it also required carefully reading the JSON files and
  updating the individual managers with the data.

 * **Code contributed**: [RepoSense link](https://nus-cs2103-ay2021s1.github.io/tp-dashboard/#breakdown=true&search=chunyongg&sort=groupTitle&sortWithin=title&since=2020-08-14&timeframe=commit&mergegroup=&groupSelect=groupByRepos&checkedFileTypes=docs~functional-code~test-code~other)
  
 * **Project management**:
   * As the team leader, I led weekly meetings and kept track of deadlines and deliverables.
   * Planned deliverables actively and kept a forward-thinking approach to ensure our team would complete our
    planned features at the end of our project.
    
 * **Enhancements to existing features**:
   * Refactored code to create `GroupName`, `LessonName`, `StudentName` and `StudentNumber` classes 
   instead of using Strings, to enforce restrictions on the type of Strings that can be used. 
   For instance, using regex to enforce a pattern on `StudentNumber` as student numbers 
   have a specific format.
    (Pull requests [\#155](https://github.com/AY2021S1-CS2103T-W12-4/tp/pull/155))
   * Wrote additional tests for existing features to increase test coverage.
    (Pull requests [\#90](https://github.com/AY2021S1-CS2103T-W12-4/tp/pull/90), 
        [\#318](https://github.com/AY2021S1-CS2103T-W12-4/tp/pull/318))
   * Improve error messages when user enters an incorrect command, 
  so that they know what part of the command is invalid.
    (Pull requests [\#260](https://github.com/AY2021S1-CS2103T-W12-4/tp/pull/260), 
        [\#302](https://github.com/AY2021S1-CS2103T-W12-4/tp/pull/302))  
   * Add better welcome messages to guide user on what are some of the possible 
   next commands to issue.
    (Pull requests [\#276](https://github.com/AY2021S1-CS2103T-W12-4/tp/pull/276)) 
      
 * **Documentation**:
   * User Guide:
        * Added more pictures to User Guide, with a new section called `Layout`.
        (Pull requests [\#159](https://github.com/AY2021S1-CS2103T-W12-4/tp/pull/159),
        [\#162](https://github.com/AY2021S1-CS2103T-W12-4/tp/pull/162),
        [\#200](https://github.com/AY2021S1-CS2103T-W12-4/tp/pull/200)) 
        * Added documentation for `addstudent`, `delstudent`.
        (Pull requests [\#110](https://github.com/AY2021S1-CS2103T-W12-4/tp/pull/110)) 
        
   * Developer Guide:
        * Added documentation for feature managers, group managers and lesson managers,
        and implement basic skeleton structure for the rest of the Developer Guide.
        (Pull requests [\#189](https://github.com/AY2021S1-CS2103T-W12-4/tp/pull/189),
         [\#203](https://github.com/AY2021S1-CS2103T-W12-4/tp/pull/203)
        ) 
                
   * Others
        * Fix dead links in the [repository website](https://ay2021s1-cs2103t-w12-4.github.io/tp), remove information about AddressBook 
        and update it to show information about Serenity.
         (Pull requests [\#184](https://github.com/AY2021S1-CS2103T-W12-4/tp/pull/184)) 
         
* **Community**:
  * PRs reviewed (with non-trivial review comments): 
    [\#41](https://github.com/AY2021S1-CS2103T-W12-4/tp/pull/41), 
    [\#174](https://github.com/AY2021S1-CS2103T-W12-4/tp/pull/174), 
    [\#198](https://github.com/AY2021S1-CS2103T-W12-4/tp/pull/198), 
  * Reported bugs and suggestions for other teams in the class (examples: 
    [1](https://github.com/AY2021S1-CS2103T-T12-4/tp/issues/143),
    [2](https://github.com/AY2021S1-CS2103T-T12-4/tp/issues/144), 
    [3](https://github.com/AY2021S1-CS2103T-T12-4/tp/issues/145),
    [4](https://github.com/AY2021S1-CS2103T-T12-4/tp/issues/146),
    [5](https://github.com/AY2021S1-CS2103T-T12-4/tp/issues/147),
    [6](https://github.com/AY2021S1-CS2103T-W13-3/tp/issues/148))
        
        
        

  


