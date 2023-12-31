#!/bin/bash

# This scirpt will setup all files for a new day

SESSION_KEY=$AoD_SESSION_KEY

YEAR=$1
DAY=$2

if [ -z "$SESSION_KEY" ];
then
    echo "Provide <AoD_SESSION_KEY>"
    exit 1
fi

if [ -z "$YEAR" ];
then
    echo "Provide year as a first argument"
    exit 1
fi

if [ -z "$DAY" ];
then
    echo "Provide day as a second argument"
    exit 1
fi

echo "Downloading day $DAY for year $YEAR..."

TEXT_DATA=$(curl "https://adventofcode.com/$YEAR/day/$DAY/input" --compressed -s \
  -H "cookie: session=$SESSION_KEY;"
)

echo "Creating files..."

# Add leading zero for days 1 - 9
printf -v DAY_FORMATTED "%02d" $DAY


# Create new solution file
SAMPLE_FILE=$(cat src/sample.kt)
# Apply year
SAMPLE_FILE="${SAMPLE_FILE//<year>/$YEAR}"
# Apply day
SAMPLE_FILE="${SAMPLE_FILE//<day>/$DAY_FORMATTED}"

if [ ! -f "src/$YEAR/${YEAR}_$DAY_FORMATTED.kt" ]
then
echo -e "$SAMPLE_FILE" > "src/$YEAR/${YEAR}_$DAY_FORMATTED.kt"
fi

# Save personal test case
echo -e "$TEXT_DATA" > "src/$YEAR/${YEAR}_$DAY_FORMATTED.txt"
truncate -s -1 "src/$YEAR/${YEAR}_$DAY_FORMATTED.txt"

# Create empty file for sample test data
echo "" > "src/$YEAR/${YEAR}_$(echo $DAY_FORMATTED)_test.txt"




