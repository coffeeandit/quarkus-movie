#!/bin/bash
export GRAALVM_HOME=~/tools/graalvm-ce-1.0.0-rc15/Contents/Home/
export JAVA_HOME=/opt/jdk1.8.0_202/
mvn clean package quarkus:dev