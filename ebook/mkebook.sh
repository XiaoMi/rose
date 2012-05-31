#!/bin/bash
CHAPTER_LIST="`ls -lx ../|grep chapter*`";

cat ../README.md > rose-handbook.md;

for CHAPTER in `echo "${CHAPTER_LIST}"`
do
CHAPTER_FILE=../${CHAPTER}/README.md;
    if [ -f "${CHAPTER_FILE}" ]; then
        echo ${CHAPTER_FILE};
	echo "## _EOF_
## _NL_" >> rose-handbook.md;
        cat ${CHAPTER_FILE} >> rose-handbook.md;
    fi
done;

convert.pl rose-handbook.md > rose-handbook.html
kindlegen rose-handbook.html
