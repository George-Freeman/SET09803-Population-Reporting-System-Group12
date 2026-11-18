# Sprint 7 - PMD Code Review

**Backlog item:** [#107 - Review code using PMD](https://github.com/George-Freeman/SET09803-Population-Reporting-System-Group12/issues/107)
**Review date:** 12 November 2026
**Tool:** PMD 7.7.0 via Maven PMD Plugin 3.26.0
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

## Recheck after updating from `develop`

The branch was updated with the latest `develop` on 16 November 2026. Re-running the same eight predefined Java categories found 995 findings across 38 Java files. The reviewed five rule IDs (`RedundantFieldInitializer`, `DoNotTerminateVM`, `AvoidCatchingGenericException`, `ControlStatementBraces`, and `UseUnderscoresInNumericLiterals`) had no findings in the updated scan. The project now includes more Java source files than the original 31-file scan, so the total finding counts are not directly comparable.

The largest remaining categories were 367 `SystemPrintln`, 201 `LocalVariableCouldBeFinal`, 134 `MethodArgumentCouldBeFinal`, 81 `CommentRequired`, and 57 `AvoidInstantiatingObjectsInLoops`. They remain out of scope for this focused review.

## Verification

| Check | Command | Result |
|---|---|---|
| PMD | Maven PMD Plugin 3.26.0 with a temporary configuration selecting the eight Java categories | Passed; PMD 7.7.0, 995 findings across 38 Java files; all five reviewed rule IDs absent |
| Tests and build | `mvn -DforkCount=0 verify` | Passed; 122 tests, 0 failures, 0 errors, 0 skipped |

The PMD scan used temporary Maven configuration selecting the eight predefined categories. The generated reports, `target/pmd.xml` and `target/reports/pmd.html`, are ignored build output.

## Local integration-test environment

The test run used an isolated Docker World database mapped to `localhost:33061` because the machine's existing MySQL X Protocol listener occupied port 33060. The integration test endpoint was temporarily adjusted for this local run and restored afterwards; the committed test continues to use the project's standard `localhost:33060` endpoint.
