[![Build Status](https://travis-ci.com/molgenis/molgenis-emx2.svg?branch=master)](https://travis-ci.com/molgenis/molgenis-emx2)

# EMX2

EMX2 is a data platform for researchers to accelerate scientific collaborations
and for bioinformaticians who want to make researchers happy. This is a reference
implementation of MOLGENIS/EMX2 data service. Status: preview (or 'beta').
Checkout the [Demo server](https://emx2.test.molgenis.org/) for a
quick look.

## Getting started

**requirements:** docker, docker-compose & node.js

```bash
git clone git@github.com:molgenis/emx2.git
cd emx2
# Start backend services:
docker-compose up
# From another tab:
cd frontend
yarn
yarn run dev
```
