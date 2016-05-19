#!/bin/bash
# Run script for CSCE 557 Program 1
# Author: John Pettenger
# Last modified: 02/09/16

# Path to the cipher text file.
CIPHER=cipher.txt

# Attempt to run
java -cp bin Driver $CIPHER

# Check if an error occured.
if [ $? -eq 1 ]; then
  echo -n "Failed to run. See above for errors. "
  echo "Make sure you compile first (./compile.sh)."
fi
