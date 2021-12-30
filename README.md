# Verik IntelliJ Plugin

![Build](https://github.com/frwang96/verik-intellij/workflows/Build/badge.svg)
[![Version](https://img.shields.io/jetbrains/plugin/v/18275-verik.svg)](https://plugins.jetbrains.com/plugin/18275-verik)
[![Downloads](https://img.shields.io/jetbrains/plugin/d/18275-verik.svg)](https://plugins.jetbrains.com/plugin/18275-verik)

<!-- Plugin description -->
The [Verik](https://verik.io) plugin provides SystemVerilog syntax highlighting and inspections for the Verik hardware
description language.
<!-- Plugin description end -->

## Local Build

To build the plugin locally from source clone this repository and execute the `buildPlugin` gradle task.

```
git clone https://github.com/frwang96/verik-intellij
cd verik-intellij
./gradlew buildPlugin
```

The plugin is built in `build/distributions`. To install the plugin open IntelliJ IDEA preferences, navigate to the
plugin tab and select Install Plugin from Disk.