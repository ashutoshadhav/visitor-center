#!/bin/bash

export MAVEN_OPTS="-Xmx256m"

mvn clean install spring-boot:run -DskipTests
