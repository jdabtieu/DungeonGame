#!/bin/bash

# Enforcing DEBUG mode off
if [[ $(grep "public static final boolean DEBUG = false" src/Main.java | wc -c) -eq 0 ]]; then
	echo "FATAL: DEBUG mode is on or not found. Set it to false in Main.java."
	echo "FATAL: STOPPING..."
	exit -1
fi

# Enforcing no jumps
if [[ $(grep 'jump\s*(' src/Main.java | wc -l) -gt 1 ]]; then
	echo "FATAL: jump function call detected in Main.java. Delete it before rebuilding."
	echo "FATAL: STOPPING..."
	exit -1
fi

echo "Starting up..."
rm -rf build
mkdir build

echo "Compiling..."
javac -sourcepath src -d build src/*.java

echo "Building..."
jar cmvf META-INF/MANIFEST.MF DungeonGame.jar -C build . assets

echo "Cleaning up..."
rm -rf build
