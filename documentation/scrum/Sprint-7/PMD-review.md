# Sprint 7 - PMD Code Review

**Backlog item:** [#107 - Review code using PMD](https://github.com/George-Freeman/SET09803-Population-Reporting-System-Group12/issues/107)  
**Review date:** 12 November 2026  
**Tool:** IntelliJ IDEA Community 2025.3 with PMDPlugin 2.1.0 installed; PMD 7.7.0 via Maven PMD Plugin 3.26.0 for the recorded scans
**Scope:** Project Java source

IntelliJ IDEA and PMDPlugin were installed, and IntelliJ confirmed the plugin loaded. The plugin's in-IDE scan action could not be run successfully, so the review scans used a temporary Maven configuration with the eight predefined Java categories: best practices, code style, design, documentation, error prone, multithreading, performance, and security. This used PMD 7.7.0; the installed plugin bundles PMD 7.21.0, so the recorded scan is not an IDE-plugin execution.

## Results

The initial scan reported 855 findings. After reviewing them, five clear, low-risk issues were fixed:

| Finding | Change |
|---|---|
| RedundantFieldInitializer | Removed the explicit `null` initializer from the static connection field |
| DoNotTerminateVM | Removed `System.exit` from `connect`; a missing driver now leaves the connection unavailable for the caller to handle |
| AvoidCatchingGenericException | Narrowed connection-close handling to `SQLException` and included the exception message |
| ControlStatementBraces | Added braces around the conditional `break` |
| UseUnderscoresInNumericLiterals | Formatted the population literal as `730_074_600L` |

The final scan completed successfully with 850 findings across 31 Java files. Most remaining findings are broad style recommendations or patterns inherent to this console application, including 320 `SystemPrintln`, 164 `LocalVariableCouldBeFinal`, 118 `MethodArgumentCouldBeFinal`, 72 `CommentRequired`, and 47 `AvoidInstantiatingObjectsInLoops` findings. These were left unchanged rather than making noisy or behavior-affecting edits solely to reduce the count.

PMD also reported that `LoosePackageCoupling` was misconfigured because no packages or classes were specified, and removed that rule from the run. The generated Maven reports are `target/pmd.xml` and `target/reports/pmd.html`; they are under the ignored `target/` directory. This document preserves the scan counts and review decisions for code review.

## Verification

| Check | Command | Result |
|---|---|---|
| PMD | Temporary Maven configuration with the eight predefined Java category rulesets | Passed; 850 findings after the selected fixes |
| Tests and build | `mvn verify` | Passed; 84 tests, 0 failures, 0 errors, 0 skipped |

## Surefire note

The first default test run failed before executing tests because Surefire could not start its forked JVM. The installed JDK path contained parentheses (`jdk-17.0.18(1)`), which caused the fork command to fail through Windows `cmd.exe`. Running the same `mvn verify` command through a temporary JDK path without parentheses succeeded; the temporary path was removed afterwards. This was an environment-specific launch issue, not a test failure.
