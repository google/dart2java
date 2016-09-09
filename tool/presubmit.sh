#!/bin/bash
fail() {
  printf "\n\n\e[31m*** Presubmit failed. ***\e[0m\n"
  exit 1
}

cd "$(dirname "${BASH_SOURCE[0]}")/.."
tool/format.sh || fail
tool/analyze.sh || fail
tool/build_sdk.sh || fail
tool/test.sh || fail

printf "\n\n\e[32m*** Presubmit finished successfully! ***\e[0m\n"
