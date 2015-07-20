Project Development Guide
=========================

This document outlines development practices for the project.

Testing
-------

Tests are run with JUnit on the local JVM.
You can run the tests with the following command:

    ./gradlew test

You cannot run instrumentation or functional (Activity/UI) tests at this time.
Tests require the `@RunWith(JUnit4.class)` flag in order to run properly.

Please include tests for any classes which include logical blocks to ensure they
are (and continue) working as intended. You can use Mockito, included in the
project to accomplish any dependency stubbing/spys necessary for tests.
Behavioral tests are not required.

Package Structure
-----------------

The package structure for this project is broken into *functional* areas.

The `com.animedetour.android` package should only contain the code that is
coupled to the Android API's in some way. The packages inside should also be
divided into the functional areas of the application.

Code that can be easily decoupled from the Android application (in theory, at
least) should be in its own package at the `com.animedetour` root.

Try to minimize code outside of the `main` sources. Code in `debug` and
`release` is typically harder to test and easy to miss when updating code.

Copyrights / Comments
---------------------

Attach the standard copyright to any new classes, and make sure to update the
year if any significant changes are made to the file.

~~~~
/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2015 Anime Twin Cities, Inc.
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
 ~~~~

Keep code well commented. Add comments explaining what classes are to be used
for, and what effect methods will have. Make sure to keep methods up-to-date
when changing logic / routines inside of the method/class.

Attach the `@author` tag to any classes that you create or significantly modify.
This helps keep track of the original author(s) throughout code changes.

Speeding up build times
-----------------------

If you are testing on a 21+ device, you can skip the proguard step in order to
significantly lower build times by adding the `-PskipProguard=true`
