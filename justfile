# do not create a shorcut for `publish` command to avoid accidental publishing
alias b := build
alias c := clean
alias d := docs
alias gd := get-dependencies
alias od := open-docs
alias pt := push-tag
alias r := release
alias us := update-sdk
alias usa := update-sdk-android
alias usal := update-sdk-android-latest
alias usi := update-sdk-ios
alias usil := update-sdk-ios-latest
alias usl := update-sdk-latest
alias v := version

# Source: https://semver.org/#is-there-a-suggested-regular-expression-regex-to-check-a-semver-string
# \ are escaped
SEMVER_REGEX := "(0|[1-9]\\d*)\\.(0|[1-9]\\d*)\\.(0|[1-9]\\d*)(?:-((?:0|[1-9]\\d*|\\d*[a-zA-Z-][0-9a-zA-Z-]*)(?:\\.(?:0|[1-9]\\d*|\\d*[a-zA-Z-][0-9a-zA-Z-]*))*))?(?:\\+([0-9a-zA-Z-]+(?:\\.[0-9a-zA-Z-]+)*))?"

# MAKE SURE YOU HAVE
# #!/usr/bin/env sh
# set -e
# AT THE TOP OF YOUR RECIPE
_ask-confirm:
  @bash -c 'read confirmation; if [[ $confirmation != "y" && $confirmation != "Y" ]]; then echo "Okay 😮‍💨 😅"; exit 1; fi'

build: get-dependencies
    npm run build

clean: _clear-node-modules
    rm package-lock.json

_clear-node-modules:
    rm -rf node_modules

docs:
    npm run docgen

get-dependencies:
    npm i

_latest-android:
    @curl -s https://s3-us-west-2.amazonaws.com/m2.hypertrack.com/com/hypertrack/sdk-android/maven-metadata-sdk-android.xml | grep latest | grep -o -E '{{SEMVER_REGEX}}' | head -n 1

_latest-ios:
    @curl -s https://cocoapods.org/pods/HyperTrack | grep -m 1 -o -E "HyperTrack <span>{{SEMVER_REGEX}}" | grep -o -E '{{SEMVER_REGEX}}' | head -n 1

open-docs: docs
    open docs/index.html

push-tag:
    #!/usr/bin/env sh
    set -euo pipefail
    if [ $(git symbolic-ref --short HEAD) = "main" ] ; then
        VERSION=$(just version)
        git tag $VERSION
        git push origin $VERSION
    else
        echo "You are not on main branch"
    fi

release publish="dry-run": build
    #!/usr/bin/env sh
    set -euo pipefail
    VERSION=$(just version)
    if [ {{publish}} = "publish" ]; then
        BRANCH=$(git branch --show-current)
        if [ $BRANCH != "main" ]; then
            echo "You must be on main branch to publish a new version (current branch: $BRANCH))"
            exit 1
        fi
        echo "Are you sure you want to publish version $VERSION? (y/N)"
        just _ask-confirm
        npm publish
        open "https://www.npmjs.com/package/hypertrack-sdk-ionic-capacitor/v/$VERSION"
        open "https://github.com/hypertrack/sdk-ionic-capacitor/releases/tag/$VERSION"
    else
        npm publish --dry-run
    fi

setup: get-dependencies

_update-readme-android android_version:
    ./scripts/update_file.sh README.md 'Android\%20SDK-.*-brightgreen.svg' 'Android%20SDK-{{android_version}}-brightgreen.svg'

_update-readme-ios ios_version:
    ./scripts/update_file.sh README.md 'iOS\%20SDK-.*-brightgreen.svg' 'iOS%20SDK-{{ios_version}}-brightgreen.svg'

update-sdk-latest wrapper_version commit="true" branch="true":
    #!/usr/bin/env sh
    set -euo pipefail
    LATEST_IOS=$(just _latest-ios)
    LATEST_ANDROID=$(just _latest-android)
    just update-sdk {{wrapper_version}} $LATEST_IOS $LATEST_ANDROID {{commit}} {{branch}}

update-sdk-android-latest wrapper_version commit="true" branch="true":
    #!/usr/bin/env sh
    set -euo pipefail
    LATEST_ANDROID=$(just _latest-android)
    just update-sdk-android {{wrapper_version}} $LATEST_ANDROID {{commit}} {{branch}}

update-sdk-ios-latest wrapper_version commit="true" branch="true":
    #!/usr/bin/env sh
    set -euo pipefail
    LATEST_IOS=$(just _latest-ios)
    just update-sdk-ios {{wrapper_version}} $LATEST_IOS {{commit}} {{branch}}

update-sdk wrapper_version ios_version android_version commit="true" branch="true": build
    #!/usr/bin/env sh
    set -euo pipefail
    if [ "{{branch}}" = "true" ] ; then
        git checkout -b update-sdk-ios-{{ios_version}}-android-{{android_version}}
    fi
    just version
    echo "New version is {{wrapper_version}}"
    just _update-wrapper-version-file {{wrapper_version}}
    ./scripts/update_changelog.sh -w {{wrapper_version}} -i {{ios_version}} -a {{android_version}}
    echo "Updating HyperTrack SDK iOS to {{ios_version}}"
    just _update-sdk-ios-version-file {{ios_version}}
    just _update-readme-ios {{ios_version}}
    echo "Updating HyperTrack SDK Android to {{android_version}}"
    just _update-sdk-android-version-file {{android_version}}
    just _update-readme-android {{android_version}}
    just docs
    if [ "{{commit}}" = "true" ] ; then
        git add .
        git commit -m "Update HyperTrack SDK iOS to {{ios_version}} and Android to {{android_version}}"
    fi

update-sdk-android wrapper_version android_version commit="true" branch="true": build
    #!/usr/bin/env sh
    set -euo pipefail
    if [ "{{branch}}" = "true" ] ; then
        git checkout -b update-sdk-android-{{android_version}}
    fi
    just version
    echo "Updating HyperTrack SDK Android to {{android_version}} on {{wrapper_version}}"
    just _update-wrapper-version-file {{wrapper_version}}
    just _update-sdk-android-version-file {{android_version}}
    just _update-readme-android {{android_version}}
    ./scripts/update_changelog.sh -w {{wrapper_version}} -a {{android_version}}
    just docs
    if [ "{{commit}}" = "true" ] ; then
        git add .
        git commit -m "Update HyperTrack SDK Android to {{android_version}}"
    fi

update-sdk-ios wrapper_version ios_version commit="true" branch="true": build
    #!/usr/bin/env sh
    set -euo pipefail
    if [ "{{branch}}" = "true" ] ; then
        git checkout -b update-sdk-ios-{{ios_version}}
    fi
    just version
    echo "Updating HyperTrack SDK iOS to {{ios_version}} on {{wrapper_version}}"
    just _update-wrapper-version-file {{wrapper_version}}
    just _update-sdk-ios-version-file {{ios_version}}
    just _update-readme-ios {{ios_version}}
    ./scripts/update_changelog.sh -w {{wrapper_version}} -i {{ios_version}}
    just docs
    if [ "{{commit}}" = "true" ] ; then
        git add .
        git commit -m "Update HyperTrack SDK iOS to {{ios_version}}"
    fi

_update-sdk-android-version-file android_version:
    ./scripts/update_file.sh android/build.gradle 'ext.hypertrack_sdk_version = ".*"' 'ext.hypertrack_sdk_version = "{{android_version}}"'

_update-sdk-ios-version-file ios_version:
    ./scripts/update_file.sh HypertrackSdkIonicCapacitor.podspec "'HyperTrack', '.*'" "'HyperTrack', '{{ios_version}}'"

_update-wrapper-version-file wrapper_version:
    ./scripts/update_file.sh package.json '"version": ".*"' '"version": "{{wrapper_version}}"'

version:
    @cat package.json | grep version | head -n 1 | grep -o -E '{{SEMVER_REGEX}}'
