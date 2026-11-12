# Sprint 7 - PMD Code Review

**Backlog item:** [#107 - Review code using PMD](https://github.com/George-Freeman/SET09803-Population-Reporting-System-Group12/issues/107)  
**Review date:** 12 November 2026  
**Tool:** PMD 7.7.0 via Maven PMD Plugin 3.26.0  
**Scope:** Project Java source

IntelliJ IDEA and its PMDPlugin were not available in the review environment; the review was run with the project's Maven-based PMD tooling instead.

## Results

PMD completed successfully and reported no problems in the source code. No code changes were needed.

The generated Maven reports were `target/pmd.xml` and `target/reports/pmd.html`. These are under the ignored `target/` build directory; this file records the result for the sprint review.

## Verification

| Check | Command | Result |
|---|---|---|
| PMD | `mvn org.apache.maven.plugins:maven-pmd-plugin:3.26.0:pmd` | Passed; PMD 7.7.0 reported no findings |
| Tests and build | `mvn verify` | Passed; 84 tests, 0 failures, 0 errors, 0 skipped |

## Surefire note

The first default test run failed before executing tests because Surefire could not start its forked JVM. The installed JDK path contained parentheses (`jdk-17.0.18(1)`), which caused the fork command to fail through Windows `cmd.exe`. Running the same `mvn verify` command through a temporary JDK path without parentheses succeeded; the temporary path was removed afterwards. This was an environment-specific launch issue, not a test failure.
