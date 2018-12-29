# Android AutoVersion (AAV) Template Project

Android template project configured based on an opinionated set of best practices for small to medium sized projects.

## App Flavors and Build types

`productFlavors` are used to build different APK's with different ids, endpoints, applicationId, etc. In general, different product flavors produce a different apk that can be installed on the same device and have different signing keys.

The sample app defines two product flavors in the apps [build.gradle](app/build.gradle) file.
* dev
* external

`buildTypes` define how the apk is built. The sample app defines two build types, one for fast development iteration, and one for optimized production releases. 
* debug
* release

Note that the buildType is orthognal to productFlavor. You can create a standard `debug` + `dev` apk, a `debug` + `external` apk or a `release` + `dev` apk.

## Semantic Version 2.0 based VersionCode and VersionName

One of the main features of this template is a gradle script that will parse and create a meaningful versionName and versionCode for a build based tags in the git repository. Annotated git tags are commonly used to mark specific commits when creating a build that will be published or released.

In the case that there are no reachable annotated tags, then the versionCode will be the number of commits from HEAD (`git rev-list --count HEAD`). If there are annotated tags, then the script will attempt to parse the human readable tag into a numeric versionCode. versionCode's, prior to Android P, are limited to 2,100,000,000 (Android P increases this). While this is a large number of possible values, parsing a human readable version tag into a versionCode integer means that individual values within the versionCode must be mapped into a fixed, reserved range within the versionCode to prevent misordering.

This logic assumes one of the following forms for version tag, based on [SemVer 2.0.0](https://semver.org/). The notable exception to the sorting requirements is that anything beyond patch version is discarded, despite the 'must sort by x' when the versionCode is computed.

```
xxxx[major]xxxx-xxxx
xxxx[major].[minor]xxxx-postfix
xxxx[major].[minor].[patch]xxxx-postfix
```

To conserve space, the minor and patch values are limited to 99 values each. Thus: 1.2.89 would be a valid version code, but 1.2.103 would not. To ensure that the computed version code is sortable, even if elements in the tag are omitted (eg, if large updates are tagged as "v2.3" instead of "2.3.0") the versionCode is always computed as if the absent values were set to "0"

VersionCode parsing examples (Based on `git describe`)
* `v1.0.0-alpha-3-defa3ad5 ` will parse into: ` 10000003`
* `v0.2.7-beta-567-e49dace2` will parse into: ` 10207567`
* `v23.6                   ` will parse into: `230600000`

VersionNames start from `git describe --dirty --always` which will append `"-dirty"` to the version name if the repository has local, uncommitted changes by default. The build script for the example app includes a `versionNameSuffix` for `debug` build types, and for `dev` build flavor.

VersionName parsing examples (Based on `git describe --dirty --always`)
* `v1.0.0-alpha-3-defa3ad5-dirty` (uncommited changes)
* `v0.2.7-beta-567-e49dace2-dev ` (`release` type and `dev    ` flavor)
* `v23.6                        ` (`release` type and `release` flavor)
* `v23.6-dev-debug              ` (`debug  ` type and `dev    ` flavor)

See [`script-git-version.gradle`](tools/script-git-version.gradle) for implemenation details.

See [`app/build.gradle`](app/build.gradle) for usage details.

## Proguard Optimizations for Dev and Release

There are two proguard configurations in the template project: debug and optimized. Within the example app, the `debug` build type is configured with the debug proguard file, and the `release`  build type uses the optimized proguard file.

See the following files for implementation details.
* [`debug.pro`](tools/debug.pro) - Fast builds and debugging.
* [`optimized.pro`](tools/optimized.pro). Agressive inlining, stripping, renaming, and optimizations.

See [`app/build.gradle`](app/build.gradle) for usage details.

## Signing and Keystore

Android Studio, by default, will sign debug apks with an internal debug certificate. However, this certificate expires after a year, and because different computers use different certificates, the app needs to be uninstalled and re-installed if the developer regularly switches between more than one computer. The sample app includes a generated debug.keystore that is *only* used for the `dev` productFlavor. This keystore file should not be copied and is for illustration purposes only.

**Warning**: You should never check you external keystore file into version control, even if it is encrypted with a secure password.

## Credits
Noteable sources of inspiration:
* [Dmytro Danylyk's "Configuring Android Project" talk](https://speakerdeck.com/dmytrodanylyk/configuring-android-project).
* [futurice@ Android Best Practice](https://github.com/futurice/android-best-practices#build-system)