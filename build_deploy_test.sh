#!/bin/bash
set -e

pushd tokens
./build.sh
popd

pushd accounts
./build.sh
popd

pushd payments
./build.sh
popd

pushd system-test
./deploy.sh
./test.sh
popd