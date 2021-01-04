#!/usr/bin/env bash

runRunCrud() {
  echo "Executing runcrud script"
  sh runcrud.sh
}

openBrowser() {
  echo "Starting Edge with getTasks page"
  open -a /Applications/Microsoft\ Edge.app/Contents/MacOS/Microsoft\ Edge http://localhost:8080/crud/v1/task/getTasks
}

if runRunCrud; then
  openBrowser
else
    echo"Błąd"
fi