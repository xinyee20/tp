---
layout: page
title: Xin Yee's Project Portfolio Page
---
### Project: Serenity
Serenity is a desktop application that helps CS2101 tutors manage their tutorial groups and lessons. 
The user interacts with it using a CLI, and it has a GUI created with JavaFX. 
It is written in Java, and has about 25k LoC
#### Summary of contributions
Given below are my contributions to the project.
* **New Feature 1**: Added the ability to mark and flag attendance of students during lessons through commands
  * What it does: 
      * `markpresent`, `markabsent` - Allows the users to mark a student present/absent when viewing a lesson
      * `flagatt`, `unflagatt` - Allows the users to flag / un-flag attendance of a student when necessary while in lesson view
  * Justification: This feature helps a user keep track of the attendance of students during tutorial lessons which is an essential feature of the product. 
  * Highlights: Initial deep nesting of `Student` information within `Group` required analysis and re-adjustment of the project structure. Optimisation of slow commands 
  that applies to all students also had to be done. These made the implementation of the feature time-consuming and challenging.
* **New Feature 2**: Added the ability to award and adjust participation scores of students during lessons
  * What it does: 
      * `addscore`, `subscore`, `editscore` - Allows the users to increase / decrease / edit the participation score of a student when viewing a lesson
  * Justification: This feature helps a user to keep track of the score of students during tutorial lessons which is an important aspect of handling a group during lesson.
  * Highlights: Implementation of feature took time as users' needs had to be taken into consideration (the command should be straightforward). Due to quite a few restrictions 
  in terms of input of score, repeated testing and adjustment of code had to be done to make sure that proper messages are shown when different exceptions are thrown to guide users.
* **New Feature 3**: Added the ability to use indexing to apply commands to existing students. 
  * What it does: Allows users to apply deleting of students when in group view and the marking, flagging attendance of students, 
  as well as awarding, adjusting participation score of students using the index of the student shown on the list when in lesson view.
  * Justification: This feature improves the product significantly as it is helps a user to apply commands to students 
  without needing to type in their name and matriculation number of students which may be long, increasing the efficiency of lessons.
  * Highlights: Implementation of this feature was rather challenging. Research on usage of `Comparator` and `LinkedHashSet` was done to 
  sort the unordered lists (`Lesson`, `Student`, `StudentInfo`) in the GUI. 
  Implementing index on top of name and matriculation number resulted in many permutations of the usage of commands, which required more rigorous testing and conditions to cover the various scenarios.

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2021s1.github.io/tp-dashboard/#breakdown=true&search=xinyee20&sort=groupTitle&sortWithin=title&since=2020-08-14&timeframe=commit&mergegroup=&groupSelect=groupByRepos&checkedFileTypes=docs~functional-code~test-code~other)

* **Project management**: 
  * Contributed to weekly team meetings regarding ideas for features, design details and implementation constraints.
  * Used Github Issues Tracker to schedule, track and assign tasks to teammates. Afterwards, review and merge PRs to close issues.
  
* **Enhancements to existing features**:
    * Detected and fixed bugs for some features. ( Pull request 
    [#87](https://github.com/AY2021S1-CS2103T-W12-4/tp/pull/87),
    [#112](https://github.com/AY2021S1-CS2103T-W12-4/tp/pull/112),
    [#172](https://github.com/AY2021S1-CS2103T-W12-4/tp/pull/172),
    [#270](https://github.com/AY2021S1-CS2103T-W12-4/tp/pull/270),
    [#279](https://github.com/AY2021S1-CS2103T-W12-4/tp/pull/279),
    [#280](https://github.com/AY2021S1-CS2103T-W12-4/tp/pull/280),
    [#291](https://github.com/AY2021S1-CS2103T-W12-4/tp/pull/291))
    * Wrote additional tests for existing features to increase test coverage. ( Pull request
    [#138](https://github.com/AY2021S1-CS2103T-W12-4/tp/pull/138),
    [#188](https://github.com/AY2021S1-CS2103T-W12-4/tp/pull/188),
    [#270](https://github.com/AY2021S1-CS2103T-W12-4/tp/pull/270),
    [#280](https://github.com/AY2021S1-CS2103T-W12-4/tp/pull/280),
    [#291](https://github.com/AY2021S1-CS2103T-W12-4/tp/pull/291),
    [#357](https://github.com/AY2021S1-CS2103T-W12-4/tp/pull/357))
    * Improve consistency of error message and code quality for certain commands to adhere to coding principles. ( Pull request 
    [#172](https://github.com/AY2021S1-CS2103T-W12-4/tp/pull/172),
    [#188](https://github.com/AY2021S1-CS2103T-W12-4/tp/pull/188),
    [#291](https://github.com/AY2021S1-CS2103T-W12-4/tp/pull/291))

* **Documentation**:
  * User Guide:
      * Added documentation for set up section. ( Pull request
      [#27](https://github.com/AY2021S1-CS2103T-W12-4/tp/pull/27))
      * Added documentation for the features `markpresent`, `markabsent`, `flagatt`, `unflagatt`, `editscore`,
      `addscore`, `subscore`. ( Pull request 
      [#149](https://github.com/AY2021S1-CS2103T-W12-4/tp/pull/149))
      * Addressed documentation issues after adjustment of commands and peer review. ( Pull request 
      [#112](https://github.com/AY2021S1-CS2103T-W12-4/tp/pull/112),
      [#120](https://github.com/AY2021S1-CS2103T-W12-4/tp/pull/120),
      [#128](https://github.com/AY2021S1-CS2103T-W12-4/tp/pull/128),
      [#350](https://github.com/AY2021S1-CS2103T-W12-4/tp/pull/350))
  * Developer Guide:
      * Added target user profile and value proposition. ( Pull request
      [#28](https://github.com/AY2021S1-CS2103T-W12-4/tp/pull/28))
      * Added implementation details for `StudentInfoManager`. ( Pull request 
      [#198](https://github.com/AY2021S1-CS2103T-W12-4/tp/pull/198))


* **Community**:
  * Reported bugs and suggestions for other teams in the class (examples: 
    [1](https://github.com/AY2021S1-CS2103T-W13-2/tp/issues/110),
    [2](https://github.com/AY2021S1-CS2103T-W13-2/tp/issues/111), 
    [3](https://github.com/AY2021S1-CS2103T-W13-2/tp/issues/112),
    [4](https://github.com/AY2021S1-CS2103T-W13-2/tp/issues/113),
    [5](https://github.com/AY2021S1-CS2103T-W13-2/tp/issues/114))

