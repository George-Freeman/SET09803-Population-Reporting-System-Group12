# Sprint 7 - PMD Code Review

**Backlog item:** [#107 - Review code using PMD](https://github.com/George-Freeman/SET09803-Population-Reporting-System-Group12/issues/107)  
**Review date:** 12 November 2026  
**Tool:** PMD 7.7.0 via Maven PMD Plugin 3.26.0  
**Scope:** Project Java source

## Results

PMD completed successfully and reported no problems in the source code. No code changes were needed.

The generated Maven reports were `target/pmd.xml` and `target/reports/pmd.html`. These are under the ignored `target/` build directory; this file records the result for the sprint review.

## Verification

| Check | Command | Result |
|---|---|---|
| PMD | `mvn org.apache.maven.plugins:maven-pmd-plugin:3.26.0:pmd` | Passed; no findings |
| Tests and build | `mvn -DforkCount=0 verify` | Passed; 84 tests, 0 failures, 0 errors, 0 skipped |

The default forked test run encountered a Surefire JVM startup failure before running tests. Running the Maven verification with `-DforkCount=0` avoided the fork and completed successfully.
