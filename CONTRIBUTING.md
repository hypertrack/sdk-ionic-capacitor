# Contributing

## FAQ

### How to update HyperTrack SDK version and make a release?

1. Update SDK version constant
   - Android
     - [android/build.gradle](android/build.gradle)
       - `dependencies {}`
         - `com.hypertrack:hypertrack:<version>`
   - iOS
     - [HypertrackSdkIonicCapacitor.podspec](HypertrackSdkIonicCapacitor.podspec)
       - `s.dependency 'HyperTrack'`
  
2. Increment wrapper version
   
   - [package.json](package.json)
     - `version`

3. Update [CHANGELOG](CHANGELOG.md)

   - **Add the release link to the bottom**

4. Update badge in [README](README.md)

5. Do the release dry run with `just release` and verify that the release is correct (checklist is in the command output)

6. Commit and merge to master

7. Create and push a new version tag

8. Create a Github repo release
   - Release title should be the current version tag
  
9. Run `npm publish` to publish the package to npm

## Scripts

### `npm run build`

Build the plugin web assets and generate plugin API documentation using [`@capacitor/docgen`](https://github.com/ionic-team/capacitor-docgen).

It will compile the TypeScript code from `src/` into ESM JavaScript in `dist/esm/`. These files are used in apps with bundlers when your plugin is imported.

Then, Rollup will bundle the code into a single file at `dist/plugin.js`. This file is used in apps without bundlers by including it as a script in `index.html`.

### `npm run verify`

Build and validate the web and native projects.

This is useful to run in CI to verify that the plugin builds for all platforms.

### `npm run lint` / `npm run fmt`

Check formatting and code quality, autoformat/autofix if possible.

This template is integrated with ESLint, Prettier, and SwiftLint. Using these tools is completely optional, but the [Capacitor Community](https://github.com/capacitor-community/) strives to have consistent code style and structure for easier cooperation.

## Publishing

There is a `prepublishOnly` hook in `package.json` which prepares the plugin before publishing, so all you need to do is run:

```shell
npm publish
```

> **Note**: The [`files`](https://docs.npmjs.com/cli/v7/configuring-npm/package-json#files) array in `package.json` specifies which files get published. If you rename files/directories or add files elsewhere, you may need to update it.
