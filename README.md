# video-app
## (Galgonator)

### Note about unit tests
Some unit tests are in a `test` package and others are in a `androidTest` package. This is intentional to fit with Android Studio's testing tools. 

Tests in the `test` package only depend on JUnit and are used for testing "normal" Java code (without Android classes). These tests run on the JVM of the host machine.

Tests in the `androidTest` package depends on Android-specific testing classes and builds a testing artifact to be loaded onto the device or emulator under test.


