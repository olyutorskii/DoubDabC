DoubDabC Changelog
===================

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).


## [Unreleased]


## [2.101.14] - 2022-10-11

### Added
- Add Javadoc API publishing workflow for GitHub Actions.
- Add manual trigger for GitHub Actions.

### Changed
- Modify JavaCI actions trigger for GitHub Actions.
- Workaround MJAVADOC-700 issue.


## [2.101.12] - 2021-05-12

### Added
- Add GitHub Actions (JavaCI and CodeQL).

### Removed
- Remove Travis CI integration.

### Changed
- Change CHANGELOG.md format to keepchangelog.com style.

### Security
- responding to junit security issue GHSA-269g-pwp5-87pp


## [2.101.10] - 2020-02-02

### Security
- responding to checkstyle security issue GHSA-763g-fqq7-48wg


## [2.101.8] - 2019-03-19

### Security
- responding to checkstyle security issue CVE-2019-9658


## [2.101.6] - 2018-08-08

### Added
- Add Mersenne prime#32 (2^756839 -1) benchmark test.

### Changed
- Move PMD & Checkstyle config files to folder.

### Fixed
- BugFix: Missing copies when 8N+1 length decimal. Fixed #25


## [2.101.4] - 2017-06-22

### Added
- add JarabraDix info to README

### Changed
- Correspond to Maven 3.5


## [2.101.2] - 2017-04-24

### Added
- Add BcdArrays

### Changed
- Merge DecimalText and DecimalOut to BcdSequence
- Split BcdUtils class from BcdRegister

### Removed
- Extended Writer class removed. See JarabraDix new project.


## [1.103.2] - 2017-03-22

### Added
- Add DecimalWriter which supports print(int) #16

### Removed
- Remove overblown terms on perfomance #20


## [1.102.6] - 2017-02-19

### Added
- Add .travis.yml for Travis CI #18
- Add header to Javadoc pages

### Changed
- Speed-up Arabic-num converting


## [1.102.4] - 2017-02-02

### Added
- Add static code analysis report plugin. (checkstyle,PMD,FindBugs)
- Add code coverage report plugin. (Jacoco)
- Add SCM info to POM. #13
- Add CHANGELOG.md #14

### Changed
- Speed-up converting


## [1.102.2] - 2017-01-03

### Added
- Initial Release


[Unreleased]: https://github.com/olyutorskii/DoubDabC/compare/v2.101.12...HEAD
[2.101.12]: https://github.com/olyutorskii/DoubDabC/compare/v2.101.10...v2.101.12
[2.101.10]: https://github.com/olyutorskii/DoubDabC/compare/v2.101.8...v2.101.10
[2.101.8]: https://github.com/olyutorskii/DoubDabC/compare/v2.101.6...v2.101.8
[2.101.6]: https://github.com/olyutorskii/DoubDabC/compare/v2.101.4...v2.101.6
[2.101.4]: https://github.com/olyutorskii/DoubDabC/compare/v2.101.2...v2.101.4
[2.101.2]: https://github.com/olyutorskii/DoubDabC/compare/v1.103.2...v2.101.2
[1.103.2]: https://github.com/olyutorskii/DoubDabC/compare/v1.102.6...v1.103.2
[1.102.6]: https://github.com/olyutorskii/DoubDabC/compare/v1.102.4...v1.102.6
[1.102.4]: https://github.com/olyutorskii/DoubDabC/compare/v1.102.2...v1.102.4
[1.102.2]: https://github.com/olyutorskii/DoubDabC/releases/tag/v1.102.2


--- EOF ---
