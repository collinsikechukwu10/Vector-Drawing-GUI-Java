#!/bin/bash
JUNITPATH="$TESTDIR"
TESTPACKAGE="controller_tests"
TESTRUNNER="ControllerTestRunner"

if [ -d "../p4-vector-drawing-tests/$TESTPACKAGE" ]; then
    echo "ERROR: directory ../p4-vector-drawing-tests/$TESTPACKAGE already exists. Please remove or rename and re-run stacscheck"
    exit 1
else
    mkdir -p ../p4-vector-drawing-tests/$TESTPACKAGE
    cp "$JUNITPATH"/$TESTPACKAGE/*.java ../p4-vector-drawing-tests/$TESTPACKAGE/

    javac -cp "$JUNITPATH/junit.jar":"$JUNITPATH/hamcrest.jar":. ../p4-vector-drawing-tests/$TESTPACKAGE/*.java
    java -cp "$JUNITPATH/junit.jar":"$JUNITPATH/hamcrest.jar":../p4-vector-drawing-tests:.  $TESTPACKAGE.$TESTRUNNER
    exitcode=$?

    rm -r ../p4-vector-drawing-tests/$TESTPACKAGE

    exit $exitcode
fi
