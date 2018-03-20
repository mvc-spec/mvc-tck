#!/bin/bash

set -euo pipefail

BASEDIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )/../" && pwd )"

echo "Creating site archive..."
cd ${BASEDIR}/tests/target/coverage-report/
mv coverage-mvc.html index.html
zip -q -r ${BASEDIR}/site.zip *

echo "Uploading archive..."
cd ${BASEDIR}
curl -s -H "Content-Type: application/zip" \
     -H "Authorization: Bearer ${ACCESS_TOKEN}" \
     --data-binary "@site.zip" \
     https://api.netlify.com/api/v1/sites/6c610354-5779-4ea6-8ceb-8606b54b0ec8/deploys \
     > /dev/null

echo "Done"
