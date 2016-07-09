# Pre-work - *Simple TODO App*

**Simple TODO App** is an android app that allows building a todo list and basic todo items management functionality including adding new items, editing and deleting an existing item.

Submitted by: **Willie Owens**

Time spent: **5** hours spent in total

## User Stories

The following **required** functionality is completed:

* [X] User can **successfully add and remove items** from the todo list
* [X] User can **tap a todo item in the list and bring up an edit screen for the todo item** and then have any changes to the text reflected in the todo list.
* [X] User can **persist todo items** and retrieve them properly on app restart

The following **optional** features are implemented:

* [X] Persist the todo items [into SQLite](http://guides.codepath.com/android/Persisting-Data-to-the-Device#sqlite) instead of a text file
* [X] Improve style of the todo items in the list [using a custom adapter](http://guides.codepath.com/android/Using-an-ArrayAdapter-with-ListView)
* [X] Add support for completion due dates for todo items (and display within listview item)
* [ ] Use a [DialogFragment](http://guides.codepath.com/android/Using-DialogFragment) instead of new Activity for editing items
* [X] Add support for selecting the priority of each todo item (and display in listview item)
* [X] Tweak the style improving the UI / UX, play with colors, images or backgrounds

The following **additional** features are implemented:

* [X] Require non-empty string to add an item - Toast displayed when requirement not satisfied
* [X] Display toast confirmation when deleting an item
* [X] Use a [DialogFragment](http://guides.codepath.com/android/Using-DialogFragment) for due date DatePicker
* [X] Sort columns upon header click
* [X] AlertDialog to confirm todo item deletion

## Video Walkthrough 

Here's a walkthrough of implemented user stories:

<img src='http://i.imgur.com/TqoJlda.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />

GIF created in Ubuntu with [Silentcast](https://github.com/colinkeenan/silentcast).

## Notes

Describe any challenges encountered while building the app:
* I was unable to show/hide the keyboard and tabled that issue. Several suggestions online did not seem to work - at least in AndroidStudio using a Nexus 5 emulator.

## License

    Copyright [2016] [Willie Owens]

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
