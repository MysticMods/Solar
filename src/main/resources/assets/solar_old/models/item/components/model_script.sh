#!/bin/sh

cd "$(dirname $0)"

gen() {
  test -f "$1.json" || printf '{"parent":"item/generated","textures":{"layer0":"solar:items/components/%s"}}\n' "$1" > "$1.json"
}

for arg in $@; do
  gen "$arg"
done