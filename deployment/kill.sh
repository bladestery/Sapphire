#!/bin/bash

kill `lsof -t -i:22346`
kill `lsof -t -i:22345`
