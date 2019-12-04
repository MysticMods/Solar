#!/bin/sh

cd "$(dirname $0)"

test -f "$1.json" && exit 1
printf '{"parent":"item/generated","textures":{"layer0":"solar:items/component/%s"}}\n' "$1" > "$1.json"