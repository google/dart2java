#!/bin/bash
set -e

cd "$(dirname "$BASH_SOURCE[0]")/.."

dart -c tool/patch_sdk.dart tool/input_sdk gen/patched_sdk
cp -r tool/input_sdk/internal/java_*.dart gen/patched_sdk/lib/internal/
