# hypertrack-capacitor-plugin

Capacitor plugin for HyperTrack generation SDKs

## Build plugin (local machine)

```bash
clone the repo git@github.com:hypertrack/sdk-ionic-capacitor.git
cd sdk-ionic-capacitor/
npm i
npm run verify:android
npm run build
```

## Install in ionic app (local machine)

```bash
cd ionic-app/
npm i <local-dir>/sdk-ionic-capacitor
npx cap sync
```