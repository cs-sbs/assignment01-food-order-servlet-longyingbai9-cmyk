#!/bin/bash

set -euo pipefail
BASE_URL="http://localhost:8080"

tmp="$(mktemp)"
status=$(curl -s -o "$tmp" -w "%{http_code}" "$BASE_URL/menu?name=NotExistFood")
body="$(cat "$tmp")"
rm -f "$tmp"

if [[ "$status" == "200" ]] && { [[ "$body" == *"No"* ]] || [[ "$body" == *"not"* ]]; }; then
  echo "PASS: empty search handled"
  exit 0
else
  echo "FAIL: empty search not handled"
  exit 1
fi
