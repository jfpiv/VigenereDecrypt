#!/bin/bash
# Compilation script for CSCE 557 Program 1
# Author: John Pettenger
# Last modified: 02/09/16

# Make the bin directory if necessary.
# Otherwise, remove old .class files if they exist.
if [[ ! -d bin ]]; then
  mkdir bin;
elif [[ -f bin/*.class ]]; then
  rm bin/*.class;
fi

echo "Compiling..."

# Attempt to compile.
javac -d bin -cp src src/Driver.java

# Check the error level and output a success or failure message.
if [ $? -eq 0 ]; then
  echo "Success. Now run \"./run.sh\""
else
  echo "Compile failed. See above for errors."
fi
