#!/bin/bash
# ===============================
# run.sh - Custom Java Runner
# ===============================

if [ -z "$1" ]; then
    echo "Usage: ./run.sh <path-to-java-file>"
    exit 1
fi

JAVA_FILE=$1
ROOT_DIR=$(dirname "$(realpath "$0")")
BIN_DIR="$ROOT_DIR/bin"
mkdir -p "$BIN_DIR"

# --- Convert path to package.class name ---
CLASS_NAME=$(echo "$JAVA_FILE" | sed "s|$ROOT_DIR/src/main/java/||;s|\.java$||;s|/|.|g")
CLASS_FILE="$BIN_DIR/$(echo "$CLASS_NAME" | tr '.' '/')".class

# --- Prepare output.txt ---
: > "$ROOT_DIR/output.txt"       # clear or create
chmod 644 "$ROOT_DIR/output.txt"

# --- Compile if needed ---
if [ ! -f "$CLASS_FILE" ] || [ "$JAVA_FILE" -nt "$CLASS_FILE" ]; then
    javac -d "$BIN_DIR" "$JAVA_FILE"
    if [ $? -ne 0 ]; then
        echo "Compilation failed! Check errors above." | tee "$ROOT_DIR/output.txt"
        chmod 444 "$ROOT_DIR/output.txt"
        exit 1
    fi
fi

# --- Run program once, capture + wrap output ---
unset JAVA_TOOL_OPTIONS
java -cp "$BIN_DIR" "$CLASS_NAME" 2>&1 | tee >(fold -s -w 100 > "$ROOT_DIR/output.txt")

chmod 444 "$ROOT_DIR/output.txt"
